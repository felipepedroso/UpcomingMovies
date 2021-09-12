package br.pedroso.upcomingmovies.moviedetails.usecases;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.domain.MoviesRepository;
import br.pedroso.upcomingmovies.domain.Movie;
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
