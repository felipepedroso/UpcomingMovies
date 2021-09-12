package br.pedroso.upcomingmovies.moviedetails.usecases;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.domain.MoviesRepository;
import io.reactivex.rxjava3.core.Single;

public class GetMovieDetails {
    private MoviesRepository moviesRepository;

    @Inject
    public GetMovieDetails(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public Single<Movie> execute(Integer movieId) {
        return moviesRepository.getMovieDetails(movieId);
    }
}
