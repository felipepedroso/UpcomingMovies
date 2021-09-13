package br.pedroso.upcomingmovies.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.domain.MoviesRepository;
import br.pedroso.upcomingmovies.network.entities.RetrofitMovieEntity;
import br.pedroso.upcomingmovies.network.services.TheMovieDbService;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviesRepositoryImpl implements MoviesRepository {
    private final TheMovieDbService theMovieDbService;

    @Inject
    public MoviesRepositoryImpl(TheMovieDbService theMovieDbService) {
        this.theMovieDbService = theMovieDbService;
    }

    @Override
    public Single<List<Movie>> listUpcomingMovies() {
        return theMovieDbService.listUpcomingMovies()
                .map(retrofitMovieEntities -> retrofitMovieEntities.stream().map(new RetrofitMovieEntityToMovieMapper()).collect(Collectors.toList()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Movie> getMovieDetails(Integer movieId) {
        return theMovieDbService.getMovieDetails(movieId)
                .map(retrofitMovieEntity -> new RetrofitMovieEntityToMovieMapper().apply(retrofitMovieEntity))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Movie>> listSimilarMovies(Integer movieId) {
        return theMovieDbService.listSimilarMovies(movieId)
                .map(retrofitMovieEntities -> retrofitMovieEntities.stream().map(new RetrofitMovieEntityToMovieMapper()).collect(Collectors.toList()))
                .subscribeOn(Schedulers.io());
    }

    private class RetrofitMovieEntityToMovieMapper implements java.util.function.Function<RetrofitMovieEntity, Movie> {
        private final String IMAGE_TMDB_BASE_URL = "https://image.tmdb.org/t/p/w500";

        private final Double MAX_VOTE_AVERAGE = 10.0;

        @Override
        public Movie apply(RetrofitMovieEntity retrofitMovieEntity) {
            return new Movie.Builder(retrofitMovieEntity.getId(), retrofitMovieEntity.getTitle())
                    .setPosterPath(IMAGE_TMDB_BASE_URL + retrofitMovieEntity.getPosterPath())
                    .setOverview(retrofitMovieEntity.getOverview())
                    .setReleaseDate(retrofitMovieEntity.getReleaseDate())
                    .setVoteAverage(retrofitMovieEntity.getVoteAverage() / MAX_VOTE_AVERAGE)
                    .build();
        }
    }
}
