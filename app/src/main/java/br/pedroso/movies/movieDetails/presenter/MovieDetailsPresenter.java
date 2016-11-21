package br.pedroso.movies.movieDetails.presenter;

import br.pedroso.movies.domain.model.Movie;
import br.pedroso.movies.domain.usecase.UseCase;
import br.pedroso.movies.movieDetails.MovieDetailsContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private MovieDetailsContract.View view;

    private UseCase<Movie> getMovieDetailsUseCase;

    private UseCase<List<Movie>> listSimilarMoviesUseCase;


    @Inject
    public MovieDetailsPresenter(MovieDetailsContract.View view, UseCase<Movie> getMovieDetailsUseCase, UseCase<List<Movie>> listSimilarMoviesUseCase) {
        this.view = view;
        this.getMovieDetailsUseCase = getMovieDetailsUseCase;
        this.listSimilarMoviesUseCase = listSimilarMoviesUseCase;
    }

    @Inject
    public void setupView() {
        view.setPresenter(this);
    }

    @Override
    public void resume() {
        loadMovieDetails();
    }

    private void loadMovieDetails() {
        getMovieDetailsUseCase.execute(getMovieDetailsSubscriber);
        listSimilarMoviesUseCase.execute(listSimilarMoviesSubscriber);
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    private void displayMovieDetails(Movie movie) {
        view.renderMovieDetails(movie);
    }


    private void displaySimilarMovies(List<Movie> movies) {
        if (!movies.isEmpty()){
            view.displaySimilarMoviesPanel();
            view.renderSimilarMovies(movies);
        }else{
            view.hideSimilarMoviesPanel();
        }
    }

    private final Subscriber<Movie> getMovieDetailsSubscriber = new Subscriber<Movie>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Movie movie) {
            displayMovieDetails(movie);
        }
    };

    private Subscriber<List<Movie>> listSimilarMoviesSubscriber = new Subscriber<List<Movie>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<Movie> movies) {
            displaySimilarMovies(movies);
        }
    };

}
