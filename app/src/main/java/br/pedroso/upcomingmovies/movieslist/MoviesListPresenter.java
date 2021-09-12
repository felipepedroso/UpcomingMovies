package br.pedroso.upcomingmovies.movieslist;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.domain.MoviesRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MoviesListPresenter {
    private static final String LOG_TAG = MoviesListPresenter.class.getName();

    private final MoviesListView view;

    private final MoviesRepository moviesRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    MoviesListPresenter(MoviesListView view, MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
        this.view = view;
    }

    public void resume() {
        loadUpcomingMovies();
    }

    private void loadUpcomingMovies() {
        view.cleanMoviesList();

        Disposable disposable = moviesRepository.listUpcomingMovies()
                .observeOn(AndroidSchedulers.mainThread()) // TODO: improve this.
                .subscribe(this::displayLoadedMovies, this::displayErrorOnLoadingMessage);

        disposables.add(disposable);
    }

    private void displayErrorOnLoadingMessage(Throwable throwable) {
        // TODO
    }

    private void displayLoadedMovies(List<Movie> movies) {
        view.renderMoviesList(movies);
    }

    public void onMovieClick(Movie movie) {
        Log.d(LOG_TAG, "Clicked on the movie: " + movie);

        view.startMovieDetailsActivity(movie.getId());
    }

    public void pause() {
        disposables.clear();
    }
}
