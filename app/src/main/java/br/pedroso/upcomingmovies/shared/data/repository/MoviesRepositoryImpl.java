package br.pedroso.upcomingmovies.shared.data.repository;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.shared.data.MoviesRepository;
import br.pedroso.upcomingmovies.shared.data.dataSource.DataSource;
import br.pedroso.upcomingmovies.domain.Movie;
import rx.Observable;
import rx.schedulers.Schedulers;

public class MoviesRepositoryImpl implements MoviesRepository {
    private DataSource dataSource;

    @Inject
    public MoviesRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Observable<List<Movie>> listUpcomingMovies() {
        return dataSource.listUpcomingMovies()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Movie> getMovieDetails(Integer movieId) {
        return dataSource.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Movie>> listSimilarMovies(Integer movieId) {
        return dataSource.listSimilarMovies(movieId)
                .subscribeOn(Schedulers.io());
    }
}
