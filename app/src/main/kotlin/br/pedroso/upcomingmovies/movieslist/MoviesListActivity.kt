package br.pedroso.upcomingmovies.movieslist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.pedroso.upcomingmovies.MoviesApplication
import br.pedroso.upcomingmovies.databinding.ActivityMoviesBinding
import br.pedroso.upcomingmovies.di.ApplicationComponent
import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsActivity
import br.pedroso.upcomingmovies.movieslist.adapter.MoviesAdapter
import br.pedroso.upcomingmovies.movieslist.di.DaggerMoviesComponent
import br.pedroso.upcomingmovies.movieslist.di.MoviesPresenterModule
import javax.inject.Inject

class MoviesListActivity : AppCompatActivity(), MoviesListView {
    private var moviesAdapter: MoviesAdapter? = null

    private var binding: ActivityMoviesBinding? = null

    @JvmField
    @Inject
    var presenter: MoviesListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectPresenter()
        setupView()
    }

    override fun onResume() {
        super.onResume()
        presenter!!.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter!!.pause()
    }

    private fun setupView() {
        binding = ActivityMoviesBinding.inflate(layoutInflater)

        setContentView(binding!!.root)

        setupRecyclerViewMovies()
    }

    private fun setupRecyclerViewMovies() {
        moviesAdapter =
            MoviesAdapter { movie: Movie -> presenter!!.onMovieClick(movie) }
        binding!!.recyclerViewMovies.adapter = moviesAdapter
    }

    private fun injectPresenter() {
        val applicationComponent: ApplicationComponent =
            (application as MoviesApplication).applicationComponent

        DaggerMoviesComponent.builder()
            .applicationComponent(applicationComponent)
            .moviesPresenterModule(MoviesPresenterModule(this))
            .build()
            .inject(this)
    }

    override fun renderMoviesList(moviesList: List<Movie>) {
        moviesAdapter?.updateAdapterData(moviesList)
    }

    override fun startMovieDetailsActivity(movieId: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, movieId)
        startActivity(intent)
    }

    override fun cleanMoviesList() {
        moviesAdapter?.clearItems()
    }
}
