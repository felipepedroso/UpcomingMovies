package br.pedroso.movies.movieDetails;

import br.pedroso.movies.mvp.BasePresenter;
import br.pedroso.movies.mvp.BaseView;
import br.pedroso.movies.domain.model.Movie;

import java.util.List;

public interface MovieDetailsContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {
        void renderMovieDetails(Movie movie);

        void hideSimilarMoviesPanel();

        void renderSimilarMovies(List<Movie> movies);

        void displaySimilarMoviesPanel();
    }
}
