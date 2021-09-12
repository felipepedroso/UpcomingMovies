package br.pedroso.upcomingmovies.moviedetails.presenter;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.domain.MoviesRepository;
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsContract;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private final MovieDetailsContract.View view;

    private final MoviesRepository moviesRepository;

    @Inject
    public MovieDetailsPresenter(MovieDetailsContract.View view, MoviesRepository moviesRepository) {
        this.view = view;
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void loadMovieDetails(int movieId) {
        moviesRepository.getMovieDetails(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayMovieDetails, this::displayLoadingErrorMessage);

        loadSimilarMovies(movieId);
    }

    private void displayLoadingErrorMessage(Throwable throwable) {
        // TODO
    }

    private void loadSimilarMovies(int movieId) {
        moviesRepository.listSimilarMovies(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displaySimilarMovies, error -> view.hideSimilarMoviesPanel());
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
