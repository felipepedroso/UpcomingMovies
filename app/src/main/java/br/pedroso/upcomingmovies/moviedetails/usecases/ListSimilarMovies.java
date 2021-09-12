package br.pedroso.upcomingmovies.moviedetails.usecases;

import java.util.List;

import br.pedroso.upcomingmovies.shared.data.MoviesRepository;
import br.pedroso.upcomingmovies.shared.domain.Movie;
import rx.Observable;

public class ListSimilarMovies {
    private final MoviesRepository moviesRepository;

    public ListSimilarMovies(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public Observable<List<Movie>> execute(Integer movieId) {
        return moviesRepository.listSimilarMovies(movieId);
    }
}
