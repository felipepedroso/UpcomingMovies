package br.pedroso.upcomingmovies.movieslist.presenter;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.movieslist.MoviesContract;
import br.pedroso.upcomingmovies.movieslist.usecases.ListUpcomingMovies;
import br.pedroso.upcomingmovies.shared.domain.Movie;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MoviesPresenter implements MoviesContract.Presenter {
    private static final String LOG_TAG = MoviesPresenter.class.getName();

    private final MoviesContract.View view;

    private final ListUpcomingMovies listUpcomingMoviesUseCase;

    @Inject
    MoviesPresenter(MoviesContract.View view, ListUpcomingMovies listUpcomingMoviesUseCase) {
        this.listUpcomingMoviesUseCase = listUpcomingMoviesUseCase;
        this.view = view;
    }

    @Override
    public void resume() {
        loadUpcomingMovies();
    }

    private void loadUpcomingMovies() {
        view.cleanMoviesList();

        Action1<? super List<Movie>> onNext = new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movies) {
                displayLoadedMovies(movies);
            }
        };

        Action1<Throwable> onError = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                displayErrorOnLoadingMessage(throwable);
            }
        };

        listUpcomingMoviesUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread()) // TODO: improve this.
                .subscribe(onNext, onError);
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
}
