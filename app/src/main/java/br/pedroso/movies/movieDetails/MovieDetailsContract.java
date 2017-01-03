package br.pedroso.movies.movieDetails;

import br.pedroso.movies.shared.mvp.BasePresenter;
import br.pedroso.movies.shared.mvp.BaseView;
import br.pedroso.movies.shared.domain.model.Movie;

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
