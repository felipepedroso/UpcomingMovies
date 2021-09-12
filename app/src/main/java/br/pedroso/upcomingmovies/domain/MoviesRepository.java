package br.pedroso.upcomingmovies.domain;

import java.util.List;

import io.reactivex.rxjava3.core.Single;


public interface MoviesRepository {
    Single<List<Movie>> listUpcomingMovies();

    Single<Movie> getMovieDetails(Integer movieId);

    Single<List<Movie>> listSimilarMovies(Integer movieId);
}
