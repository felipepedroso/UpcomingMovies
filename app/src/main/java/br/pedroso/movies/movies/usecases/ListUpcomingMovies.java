package br.pedroso.movies.movies.usecases;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.movies.shared.data.MoviesRepository;
import br.pedroso.movies.shared.domain.Movie;
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
