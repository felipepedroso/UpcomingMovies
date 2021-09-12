package br.pedroso.upcomingmovies.moviedetails;

import java.util.List;

import br.pedroso.upcomingmovies.shared.domain.Movie;

public interface MovieDetailsContract {
    interface Presenter {
        void loadMovieDetails(int movieId);
    }

    interface View {
        void renderMovieDetails(Movie movie);

        void hideSimilarMoviesPanel();

        void renderSimilarMovies(List<Movie> movies);

        void displaySimilarMoviesPanel();
    }
}
