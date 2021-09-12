package br.pedroso.movies.movieDetails.usecases;

import javax.inject.Inject;

import br.pedroso.movies.shared.data.MoviesRepository;
import br.pedroso.movies.shared.domain.Movie;
import rx.Observable;

public class GetMovieDetails {
    private MoviesRepository moviesRepository;

    @Inject
    public GetMovieDetails(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public Observable<Movie> execute(Integer movieId) {
        return moviesRepository.getMovieDetails(movieId);
    }
}
