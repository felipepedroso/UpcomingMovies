package br.pedroso.movies.shared.data;

import br.pedroso.movies.shared.domain.Movie;

import java.util.List;

import rx.Observable;

public interface MoviesRepository {
    Observable<List<Movie>> listUpcomingMovies();

    Observable<Movie> getMovieDetails(Integer movieId);

    Observable<List<Movie>> listSimilarMovies(Integer movieId);
}
