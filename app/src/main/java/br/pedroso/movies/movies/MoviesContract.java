package br.pedroso.movies.movies;

import br.pedroso.movies.shared.mvp.BasePresenter;
import br.pedroso.movies.shared.mvp.BaseView;
import br.pedroso.movies.shared.domain.model.Movie;

import java.util.List;

public interface MoviesContract {
    interface Presenter extends BasePresenter {
        void onMovieClick(Movie movie);
    }

    interface View extends BaseView<Presenter> {
        void renderMoviesList(List<Movie> moviesList);

        void startMovieDetailsActivity(Integer id);
    }
}
