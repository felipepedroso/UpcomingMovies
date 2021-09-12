package br.pedroso.upcomingmovies.movieslist;

import java.util.List;

import br.pedroso.upcomingmovies.domain.Movie;

public interface MoviesListView {
    void renderMoviesList(List<Movie> moviesList);

    void startMovieDetailsActivity(Integer id);

    void cleanMoviesList();
}
