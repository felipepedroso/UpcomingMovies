package br.pedroso.upcomingmovies.moviedetails

import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MoviesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class MovieDetailsPresenter @Inject constructor(
    private val view: MovieDetailsView,
    private val moviesRepository: MoviesRepository
) {
    private val disposables = CompositeDisposable()

    fun loadMovieDetails(movieId: Int) {
        val disposable = moviesRepository.getMovieDetails(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movie: Movie -> this.displayMovieDetails(movie) },
                { throwable: Throwable -> this.displayLoadingErrorMessage(throwable) })

        disposables.add(disposable)

        loadSimilarMovies(movieId)
    }

    private fun displayLoadingErrorMessage(throwable: Throwable) {
        // TODO
    }

    private fun loadSimilarMovies(movieId: Int) {
        val disposable = moviesRepository.listSimilarMovies(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movies: List<Movie> -> this.displaySimilarMovies(movies) },
                { error: Throwable? -> view.hideSimilarMoviesPanel() })

        disposables.add(disposable)
    }

    private fun displayMovieDetails(movie: Movie) {
        view.renderMovieDetails(movie)
    }

    private fun displaySimilarMovies(movies: List<Movie>) {
        if (movies.isNotEmpty()) {
            view.displaySimilarMoviesPanel()
            view.renderSimilarMovies(movies)
        } else {
            view.hideSimilarMoviesPanel()
        }
    }

    fun pause() {
        disposables.clear()
    }

    fun clickedOnSimilarMovie(movie: Movie) {
        view.startMovieDetailsActivity(movie.id)
    }
}
