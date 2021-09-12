package br.pedroso.upcomingmovies.movieslist.presenter;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.domain.MoviesRepository;
import br.pedroso.upcomingmovies.movieslist.MoviesContract;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MoviesPresenter implements MoviesContract.Presenter {
    private static final String LOG_TAG = MoviesPresenter.class.getName();

    private final MoviesContract.View view;

    private final MoviesRepository moviesRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    MoviesPresenter(MoviesContract.View view, MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
        this.view = view;
    }

    @Override
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

    @Override
    public void onMovieClick(Movie movie) {
        Log.d(LOG_TAG, "Clicked on the movie: " + movie);

        view.startMovieDetailsActivity(movie.getId());
    }

    public void pause() {
        disposables.clear();
    }
}
