package br.pedroso.movies.movieDetails.presenter;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.movies.movieDetails.MovieDetailsContract;
import br.pedroso.movies.movieDetails.usecases.GetMovieDetails;
import br.pedroso.movies.movieDetails.usecases.ListSimilarMovies;
import br.pedroso.movies.shared.domain.Movie;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private MovieDetailsContract.View view;

    private GetMovieDetails getMovieDetailsUseCase;

    private ListSimilarMovies listSimilarMoviesUseCase;

    @Inject
    public MovieDetailsPresenter(MovieDetailsContract.View view, GetMovieDetails getMovieDetailsUseCase, ListSimilarMovies listSimilarMoviesUseCase) {
        this.view = view;
        this.getMovieDetailsUseCase = getMovieDetailsUseCase;
        this.listSimilarMoviesUseCase = listSimilarMoviesUseCase;
    }

    @Override
    public void loadMovieDetails(int movieId) {
        Action1<? super Movie> onNext = new Action1<Movie>() {
            @Override
            public void call(Movie movie) {
                displayMovieDetails(movie);
            }
        };

        Action1<Throwable> onError = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                displayLoadingErrorMessage(throwable);
            }
        };

        getMovieDetailsUseCase.execute(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);

        loadSimilarMovies(movieId);
    }

    private void displayLoadingErrorMessage(Throwable throwable) {
        // TODO
    }

    private void loadSimilarMovies(int movieId) {
        Action1<? super List<Movie>> onNext = new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movies) {
                displaySimilarMovies(movies);
            }
        };

        Action1<Throwable> onError = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                view.hideSimilarMoviesPanel();
            }
        };

        listSimilarMoviesUseCase.execute(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    private void displayMovieDetails(Movie movie) {
        view.renderMovieDetails(movie);
    }

    private void displaySimilarMovies(List<Movie> movies) {
        if (!movies.isEmpty()) {
            view.displaySimilarMoviesPanel();
            view.renderSimilarMovies(movies);
        } else {
            view.hideSimilarMoviesPanel();
        }
    }
}
