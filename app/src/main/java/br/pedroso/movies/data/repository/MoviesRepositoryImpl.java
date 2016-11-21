package br.pedroso.movies.data.repository;

import br.pedroso.movies.data.dataSource.DataSource;
import br.pedroso.movies.domain.model.Movie;
import br.pedroso.movies.domain.repository.MoviesRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class MoviesRepositoryImpl implements MoviesRepository {
    private DataSource dataSource;

    @Inject
    public MoviesRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Observable<List<Movie>> listUpcomingMovies() {
        return dataSource.listUpcomingMovies();
    }

    @Override
    public Observable<Movie> getMovieDetails(Integer movieId) {
        return dataSource.getMovieDetails(movieId);
    }

    @Override
    public Observable<List<Movie>> listSimilarMovies(Integer movieId) {
        return dataSource.listSimilarMovies(movieId);
    }
}
