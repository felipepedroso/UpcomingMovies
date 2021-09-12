package br.pedroso.upcomingmovies.movieslist.usecases;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.domain.MoviesRepository;
import io.reactivex.rxjava3.core.Single;

public class ListUpcomingMovies {

    private MoviesRepository moviesRepository;

    @Inject
    public ListUpcomingMovies(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public Single<List<Movie>> execute() {
        return moviesRepository.listUpcomingMovies();
    }
}
