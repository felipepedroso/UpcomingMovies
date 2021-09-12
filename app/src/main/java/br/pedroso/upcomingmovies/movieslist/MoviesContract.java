package br.pedroso.upcomingmovies.movieslist;

import java.util.List;

import br.pedroso.upcomingmovies.domain.Movie;

public interface MoviesContract {
    interface Presenter {
        void onMovieClick(Movie movie);

        void resume();
    }

    interface View {
        void renderMoviesList(List<Movie> moviesList);

        void startMovieDetailsActivity(Integer id);

        void cleanMoviesList();
    }
}
