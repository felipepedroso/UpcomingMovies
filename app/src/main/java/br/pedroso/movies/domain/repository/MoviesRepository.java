package br.pedroso.movies.domain.repository;

import br.pedroso.movies.domain.model.Movie;

import java.util.List;

import rx.Observable;

public interface MoviesRepository {
    Observable<List<Movie>> listUpcomingMovies();

    Observable<Movie> getMovieDetails(Integer movieId);

    Observable<List<Movie>> listSimilarMovies(Integer movieId);
}
