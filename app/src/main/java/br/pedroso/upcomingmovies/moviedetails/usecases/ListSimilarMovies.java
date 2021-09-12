package br.pedroso.upcomingmovies.moviedetails.usecases;

import java.util.List;

import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.domain.MoviesRepository;
import io.reactivex.rxjava3.core.Single;

public class ListSimilarMovies {
    private final MoviesRepository moviesRepository;

    public ListSimilarMovies(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public Single<List<Movie>> execute(Integer movieId) {
        return moviesRepository.listSimilarMovies(movieId);
    }
}
