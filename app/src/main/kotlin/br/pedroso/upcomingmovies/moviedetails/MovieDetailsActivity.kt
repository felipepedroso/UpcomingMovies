package br.pedroso.upcomingmovies.moviedetails

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.palette.graphics.Palette
import br.pedroso.upcomingmovies.R
import br.pedroso.upcomingmovies.databinding.ActivityMovieDetailsBinding
import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MovieDetails
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiEvent.ClickedOnNavigateBack
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiEvent.ClickedOnSimilarMovie
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsViewModelEvent.NavigateBack
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsViewModelEvent.NavigateToMovieDetails
import br.pedroso.upcomingmovies.moviedetails.adapter.SimilarMoviesAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private val vieModel: MovieDetailsViewModel by viewModels()

    private var similarMoviesAdapter: SimilarMoviesAdapter? = null

    private val binding: ActivityMovieDetailsBinding by lazy {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        setupView()

        observeUiState()
        observeViewModelEvents()
    }

    private fun observeViewModelEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vieModel.viewModelEvent.collect { event ->
                    when (event) {
                        is NavigateBack -> finish()
                        is NavigateToMovieDetails -> openMovieDetails(
                            context = this@MovieDetailsActivity,
                            movieId = event.movie.id
                        )
                    }
                }
            }
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vieModel.uiState.collect { uiState ->
                    when (uiState) {
                        is MovieDetailsUiState.DisplayMovieDetails -> displayMovieMetadata(uiState.movieDetails)
                        MovieDetailsUiState.Loading -> Unit // TODO: Implement loading state
                        is MovieDetailsUiState.Error -> Unit // TODO: Implement error state
                    }
                }
            }
        }
    }

    private fun setupView() {
        setContentView(binding.root)

        setupToolbar()

        setupSimilarMoviesRecyclerView()
    }

    private fun setupSimilarMoviesRecyclerView() {
        similarMoviesAdapter = SimilarMoviesAdapter { movie: Movie ->
            vieModel.onUiEvent(ClickedOnSimilarMovie(movie))
        }
        binding.movieDetailsContent.recyclerViewSimilarMovies.adapter = similarMoviesAdapter
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarMovieDetails)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.toolbarMovieDetails.setNavigationOnClickListener {
            vieModel.onUiEvent(ClickedOnNavigateBack)
        }
    }

    private fun displayMovieMetadata(movieDetails: MovieDetails) {
        val (movie, similarMovies) = movieDetails
        val title = movie.title
        binding.movieDetailsContent.textViewMovieTitle.text = title
        binding.collapsingToolbarMovieDetails.title = title

        val resources = resources

        binding.movieDetailsContent.textViewMovieReleaseDate.text = movie.releaseDate

        val numStars = resources.getInteger(R.integer.rating_bar_num_stars)

        val votingAverage = (numStars * movie.voteAverage!!).toFloat()

        binding.movieDetailsContent.ratingBarMovieVoteAverage.rating = votingAverage

        val votingAverageText =
            String.format(resources.getString(R.string.movie_rating_format), votingAverage)

        binding.movieDetailsContent.textViewMovieVoteAverage.text = votingAverageText

        binding.movieDetailsContent.textViewMovieOverview.text = movie.overview

        Picasso.with(this).load(movie.posterPath)
            .into(binding.imageViewMoviePoster, object : Callback {
                override fun onSuccess() {
                    val bitmap =
                        (binding.imageViewMoviePoster.drawable as BitmapDrawable).bitmap
                    Palette.from(bitmap).generate { palette: Palette? ->
                        this@MovieDetailsActivity.applyPalette(palette)
                    }
                }

                override fun onError() {
                    // TODO: handle error properly.
                }
            })

        similarMoviesAdapter!!.updateAdapterData(similarMovies)
    }

    private fun applyPalette(palette: Palette?) {
        val primaryDark = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        val primary = ContextCompat.getColor(this, R.color.colorPrimary)

        val darkMutedColor = palette!!.getDarkMutedColor(primaryDark)

        binding.collapsingToolbarMovieDetails.setContentScrimColor(palette.getMutedColor(primary))
        binding.collapsingToolbarMovieDetails.setStatusBarScrimColor(darkMutedColor)

        binding.imageViewMoviePoster.drawable.colorFilter =
            PorterDuffColorFilter(palette.getLightMutedColor(primary), PorterDuff.Mode.MULTIPLY)

        window.statusBarColor = darkMutedColor
    }

    companion object {
        const val EXTRA_MOVIE_ID: String = "movie_id"

        fun openMovieDetails(context: Context, movieId: Int) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            context.startActivity(intent)
        }
    }
}
