package br.pedroso.upcomingmovies.moviedetails;

import java.util.List;

import br.pedroso.upcomingmovies.domain.Movie;

public interface MovieDetailsView {
    void renderMovieDetails(Movie movie);

    void hideSimilarMoviesPanel();

    void renderSimilarMovies(List<Movie> movies);

    void displaySimilarMoviesPanel();
}
