package br.pedroso.upcomingmovies.movieslist

import android.util.Log
import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MoviesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class MoviesListPresenter @Inject internal constructor(
    private val view: MoviesListView,
    private val moviesRepository: MoviesRepository
) {
    private val disposables = CompositeDisposable()

    fun resume() {
        loadUpcomingMovies()
    }

    private fun loadUpcomingMovies() {
        view.cleanMoviesList()

        val disposable = moviesRepository.listUpcomingMovies()
            .observeOn(AndroidSchedulers.mainThread()) // TODO: improve this.
            .subscribe(
                { movies: List<Movie> -> this.displayLoadedMovies(movies) },
                { throwable: Throwable -> this.displayErrorOnLoadingMessage(throwable) })

        disposables.add(disposable)
    }

    private fun displayErrorOnLoadingMessage(throwable: Throwable) {
        Log.e(MoviesListPresenter::class.simpleName, "Failed to load movies.", throwable)
    }

    private fun displayLoadedMovies(movies: List<Movie>) {
        view.renderMoviesList(movies)
    }

    fun onMovieClick(movie: Movie) {
        Log.d(LOG_TAG, "Clicked on the movie: $movie")

        view.startMovieDetailsActivity(movie.id)
    }

    fun pause() {
        disposables.clear()
    }

    companion object {
        private val LOG_TAG: String = MoviesListPresenter::class.java.name
    }
}
