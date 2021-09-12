package br.pedroso.upcomingmovies.movieslist.usecases;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.shared.data.MoviesRepository;
import br.pedroso.upcomingmovies.shared.domain.Movie;
import rx.Observable;

public class ListUpcomingMovies {

    private MoviesRepository moviesRepository;

    @Inject
    public ListUpcomingMovies(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public Observable<List<Movie>> execute() {
        return moviesRepository.listUpcomingMovies();
    }
}
