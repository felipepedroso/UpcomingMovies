package br.pedroso.upcomingmovies.repository;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.domain.MoviesRepository;
import br.pedroso.upcomingmovies.network.entities.RetrofitMovieEntity;
import br.pedroso.upcomingmovies.network.services.TheMovieDbService;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MoviesRepositoryImpl implements MoviesRepository {
    private TheMovieDbService theMovieDbService;

    @Inject
    public MoviesRepositoryImpl(TheMovieDbService theMovieDbService) {
        this.theMovieDbService = theMovieDbService;
    }

    @Override
    public Observable<List<Movie>> listUpcomingMovies() {
        return theMovieDbService.listUpcomingMovies()
                .flatMap(new Func1<List<RetrofitMovieEntity>, Observable<RetrofitMovieEntity>>() {
                    @Override
                    public Observable<RetrofitMovieEntity> call(List<RetrofitMovieEntity> retrofitMovieEntities) {
                        return Observable.from(retrofitMovieEntities);
                    }
                }).map(new RetrofitMovieEntityToMovieMapper())
                .toList()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Movie> getMovieDetails(Integer movieId) {
        return theMovieDbService.getMovieDetails(movieId)
                .map(new RetrofitMovieEntityToMovieMapper())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Movie>> listSimilarMovies(Integer movieId) {
        return theMovieDbService.listSimilarMovies(movieId)
                .flatMap(new Func1<List<RetrofitMovieEntity>, Observable<RetrofitMovieEntity>>() {
                    @Override
                    public Observable<RetrofitMovieEntity> call(List<RetrofitMovieEntity> retrofitMovieEntities) {
                        return Observable.from(retrofitMovieEntities);
                    }
                }).map(new RetrofitMovieEntityToMovieMapper())
                .toList()
                .subscribeOn(Schedulers.io());
    }

    private class RetrofitMovieEntityToMovieMapper implements Func1<RetrofitMovieEntity, Movie> {
        public static final String IMAGE_TMDB_BASE_URL = "https://image.tmdb.org/t/p/w500";

        @Override
        public Movie call(RetrofitMovieEntity retrofitMovieEntity) {
            Movie movie = new Movie.Builder(retrofitMovieEntity.getId(), retrofitMovieEntity.getTitle())
                    .setPosterPath(IMAGE_TMDB_BASE_URL + retrofitMovieEntity.getPosterPath())
                    .setOverview(retrofitMovieEntity.getOverview())
                    .setReleaseDate(retrofitMovieEntity.getReleaseDate())
                    .setVoteAverage(retrofitMovieEntity.getVoteAverage())
                    .build();

            return movie;
        }
    }
}
