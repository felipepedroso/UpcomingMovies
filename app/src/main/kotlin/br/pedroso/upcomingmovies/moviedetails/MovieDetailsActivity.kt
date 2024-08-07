package br.pedroso.upcomingmovies.moviedetails

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import br.pedroso.upcomingmovies.MoviesApplication
import br.pedroso.upcomingmovies.R
import br.pedroso.upcomingmovies.databinding.ActivityMovieDetailsBinding
import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.moviedetails.adapter.SimilarMoviesAdapter
import br.pedroso.upcomingmovies.moviedetails.di.DaggerMovieDetailsComponent
import br.pedroso.upcomingmovies.moviedetails.di.MovieDetailsPresenterModule
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsView {
    @JvmField
    @Inject
    var presenter: MovieDetailsPresenter? = null

    private var similarMoviesAdapter: SimilarMoviesAdapter? = null

    private var binding: ActivityMovieDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        injectPresenter()

        setupView()
    }

    private fun setupView() {
        binding = ActivityMovieDetailsBinding.inflate(
            layoutInflater
        )

        setContentView(binding!!.root)

        setupToolbar()

        setupSimilarMoviesRecyclerView()
    }

    private fun setupSimilarMoviesRecyclerView() {
        similarMoviesAdapter = SimilarMoviesAdapter { movie: Movie ->
            presenter!!.clickedOnSimilarMovie(movie)
        }
        binding!!.movieDetailsContent.recyclerViewSimilarMovies.adapter = similarMoviesAdapter
    }

    private fun setupToolbar() {
        setSupportActionBar(binding!!.toolbarMovieDetails)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding!!.toolbarMovieDetails.setNavigationOnClickListener { onBackPressed() }
    }

    private fun injectPresenter() {
        val applicationComponent = (application as MoviesApplication).applicationComponent

        DaggerMovieDetailsComponent.builder()
            .applicationComponent(applicationComponent)
            .movieDetailsPresenterModule(MovieDetailsPresenterModule(this))
            .build()
            .inject(this)
    }

    override fun renderMovieDetails(movie: Movie) {
        val title = movie.title
        binding!!.movieDetailsContent.textViewMovieTitle.text = title
        binding!!.collapsingToolbarMovieDetails.title = title

        val resources = resources

        binding!!.movieDetailsContent.textViewMovieReleaseDate.text = movie.releaseDate

        val numStars = resources.getInteger(R.integer.rating_bar_num_stars)

        val votingAverage = (numStars * movie.voteAverage!!).toFloat()

        binding!!.movieDetailsContent.ratingBarMovieVoteAverage.rating = votingAverage

        val votingAverageText =
            String.format(resources.getString(R.string.movie_rating_format), votingAverage)

        binding!!.movieDetailsContent.textViewMovieVoteAverage.text = votingAverageText

        binding!!.movieDetailsContent.textViewMovieOverview.text = movie.overview

        Picasso.with(this).load(movie.posterPath)
            .into(binding!!.imageViewMoviePoster, object : Callback {
                override fun onSuccess() {
                    val bitmap = (binding!!.imageViewMoviePoster.drawable as BitmapDrawable).bitmap
                    Palette.from(bitmap).generate { palette: Palette? ->
                        this@MovieDetailsActivity.applyPalette(palette)
                    }
                }

                override fun onError() {
                    // TODO: handle error properly.
                }
            })
    }

    private fun applyPalette(palette: Palette?) {
        val primaryDark = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        val primary = ContextCompat.getColor(this, R.color.colorPrimary)

        val darkMutedColor = palette!!.getDarkMutedColor(primaryDark)

        binding!!.collapsingToolbarMovieDetails.setContentScrimColor(palette.getMutedColor(primary))
        binding!!.collapsingToolbarMovieDetails.setStatusBarScrimColor(darkMutedColor)

        binding!!.imageViewMoviePoster.drawable.colorFilter =
            PorterDuffColorFilter(palette.getLightMutedColor(primary), PorterDuff.Mode.MULTIPLY)

        window.statusBarColor = darkMutedColor
    }

    override fun hideSimilarMoviesPanel() {
//        cardViewSimilarMovies.setVisibility(View.GONE);
    }

    override fun displaySimilarMoviesPanel() {
//        cardViewSimilarMovies.setVisibility(View.VISIBLE);
    }

    override fun startMovieDetailsActivity(id: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(EXTRA_MOVIE_ID, id)
        startActivity(intent)
    }

    override fun renderSimilarMovies(movies: List<Movie>) {
        similarMoviesAdapter!!.updateAdapterData(movies)
    }

    override fun onResume() {
        super.onResume()

        val movieToBePresentedId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)

        presenter!!.loadMovieDetails(movieToBePresentedId)
    }

    override fun onPause() {
        super.onPause()

        presenter!!.pause()
    }

    companion object {
        const val EXTRA_MOVIE_ID: String = "movie_id"
    }
}
