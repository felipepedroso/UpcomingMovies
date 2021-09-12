package br.pedroso.upcomingmovies.moviedetails;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.domain.MoviesRepository;
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MovieDetailsPresenter {

    private final MovieDetailsView view;

    private final MoviesRepository moviesRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public MovieDetailsPresenter(MovieDetailsView view, MoviesRepository moviesRepository) {
        this.view = view;
        this.moviesRepository = moviesRepository;
    }

    public void loadMovieDetails(int movieId) {
        Disposable disposable = moviesRepository.getMovieDetails(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayMovieDetails, this::displayLoadingErrorMessage);

        disposables.add(disposable);

        loadSimilarMovies(movieId);
    }

    private void displayLoadingErrorMessage(Throwable throwable) {
        // TODO
    }

    private void loadSimilarMovies(int movieId) {
        Disposable disposable = moviesRepository.listSimilarMovies(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displaySimilarMovies, error -> view.hideSimilarMoviesPanel());

        disposables.add(disposable);
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

    public void pause() {
        disposables.clear();
    }
}
