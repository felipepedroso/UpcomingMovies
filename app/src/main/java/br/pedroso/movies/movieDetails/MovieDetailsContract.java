package br.pedroso.movies.movieDetails;

import java.util.List;

import br.pedroso.movies.shared.domain.Movie;

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
