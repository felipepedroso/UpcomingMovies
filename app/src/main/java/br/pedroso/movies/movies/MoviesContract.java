package br.pedroso.movies.movies;

import java.util.List;

import br.pedroso.movies.shared.domain.model.Movie;

public interface MoviesContract {
    interface Presenter {
        void onMovieClick(Movie movie);

        void resume();
    }

    interface View {
        void renderMoviesList(List<Movie> moviesList);

        void startMovieDetailsActivity(Integer id);
    }
}
