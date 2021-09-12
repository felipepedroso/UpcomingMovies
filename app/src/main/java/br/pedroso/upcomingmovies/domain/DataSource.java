package br.pedroso.upcomingmovies.domain;

import java.util.List;

import rx.Observable;

public interface DataSource {
    Observable<List<Movie>> listUpcomingMovies();

    Observable<Movie> getMovieDetails(Integer movieId);

    Observable<List<Movie>> listSimilarMovies(Integer movieId);
}