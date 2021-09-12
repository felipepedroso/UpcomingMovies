package br.pedroso.upcomingmovies.movieslist.presenter;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.movieslist.MoviesContract;
import br.pedroso.upcomingmovies.movieslist.usecases.ListUpcomingMovies;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

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

        listUpcomingMoviesUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread()) // TODO: improve this.
                .subscribe(this::displayLoadedMovies, this::displayErrorOnLoadingMessage);
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
