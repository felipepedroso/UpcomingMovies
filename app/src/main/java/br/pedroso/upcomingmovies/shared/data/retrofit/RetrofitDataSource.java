package br.pedroso.upcomingmovies.shared.data.retrofit;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.domain.DataSource;
import br.pedroso.upcomingmovies.shared.data.retrofit.entities.RetrofitMovieEntity;
import br.pedroso.upcomingmovies.shared.data.retrofit.services.TheMovieDbService;
import br.pedroso.upcomingmovies.domain.Movie;
import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

public class RetrofitDataSource implements DataSource {
    private final TheMovieDbService theMovieDbService;
    private Retrofit retrofit;

    @Inject
    public RetrofitDataSource(Retrofit retrofit) {
        this.retrofit = retrofit;
        theMovieDbService = retrofit.create(TheMovieDbService.class);
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
                .toList();
    }

    @Override
    public Observable<Movie> getMovieDetails(Integer movieId) {
        return theMovieDbService.getMovieDetails(movieId)
                .map(new RetrofitMovieEntityToMovieMapper());
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
                .toList();
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
