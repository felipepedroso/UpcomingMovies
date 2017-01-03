package br.pedroso.movies.movies.presenter;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.movies.movies.MoviesContract;
import br.pedroso.movies.shared.domain.model.Movie;
import br.pedroso.movies.shared.domain.usecase.UseCase;
import rx.Subscriber;

public class MoviesPresenter implements MoviesContract.Presenter {
    private static final String LOG_TAG = MoviesPresenter.class.getName();
    private final MoviesContract.View view;
    private final UseCase<List<Movie>> listUpcomingMoviesUseCase;

    @Inject
    MoviesPresenter(MoviesContract.View view, UseCase<List<Movie>> listUpcomingMoviesUseCase) {
        this.listUpcomingMoviesUseCase = listUpcomingMoviesUseCase;
        this.view = view;
    }

    @Override
    public void resume() {
        loadUpcomingMovies();
    }

    private void loadUpcomingMovies() {
        listUpcomingMoviesUseCase.execute(listUpcomingMoviesSubscriber);
    }

    private void displayLoadedMovies(List<Movie> movies) {
        view.renderMoviesList(movies);
    }

    @Override
    public void onMovieClick(Movie movie) {
        Log.d(LOG_TAG, "Clicked on the movie: " + movie);

        view.startMovieDetailsActivity(movie.getId());
    }

    private final Subscriber<List<Movie>> listUpcomingMoviesSubscriber = new Subscriber<List<Movie>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<Movie> movies) {
            displayLoadedMovies(movies);
        }
    };


}
