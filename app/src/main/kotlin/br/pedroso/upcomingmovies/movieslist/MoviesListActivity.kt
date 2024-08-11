package br.pedroso.upcomingmovies.movieslist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.pedroso.upcomingmovies.MoviesApplication
import br.pedroso.upcomingmovies.databinding.ActivityMoviesBinding
import br.pedroso.upcomingmovies.di.ApplicationComponent
import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsActivity
import br.pedroso.upcomingmovies.movieslist.MoviesListUiEvent.ClickedOnMovie
import br.pedroso.upcomingmovies.movieslist.MoviesListUiState.DisplayMovies
import br.pedroso.upcomingmovies.movieslist.MoviesListUiState.Empty
import br.pedroso.upcomingmovies.movieslist.MoviesListUiState.Error
import br.pedroso.upcomingmovies.movieslist.MoviesListUiState.Loading
import br.pedroso.upcomingmovies.movieslist.MoviesListViewModelEvent.NavigateToMovieDetails
import br.pedroso.upcomingmovies.movieslist.adapter.MoviesAdapter
import br.pedroso.upcomingmovies.movieslist.di.DaggerMoviesComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesListActivity : AppCompatActivity() {
    private var moviesAdapter: MoviesAdapter? = null

    private val binding: ActivityMoviesBinding by lazy {
        ActivityMoviesBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: MoviesListViewModel.Factory

    private val viewModel: MoviesListViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        setupView()
        observeUiState()
        observeViewModelEvents()
    }

    private fun observeViewModelEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewModelEvent.collect { event ->
                    when (event) {
                        is NavigateToMovieDetails -> MovieDetailsActivity.openMovieDetails(
                            context = this@MoviesListActivity,
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
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is DisplayMovies -> renderMoviesList(uiState.movies)
                        Empty -> Unit // TODO: Implement empty state
                        is Error -> Unit // TODO: Implement error state
                        Loading -> Unit // TODO: Implement loading state
                    }
                }
            }
        }
    }

    private fun setupView() {
        setContentView(binding.root)
        setupRecyclerViewMovies()
    }

    private fun setupRecyclerViewMovies() {
        moviesAdapter = MoviesAdapter { movie: Movie -> viewModel.onUiEvent(ClickedOnMovie(movie)) }
        binding.recyclerViewMovies.adapter = moviesAdapter
    }

    private fun injectDependencies() {
        val applicationComponent: ApplicationComponent =
            (application as MoviesApplication).applicationComponent

        DaggerMoviesComponent.builder()
            .applicationComponent(applicationComponent)
            .build()
            .inject(this)
    }

    private fun renderMoviesList(moviesList: List<Movie>) {
        moviesAdapter?.updateAdapterData(moviesList)
    }
}
