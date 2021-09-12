package br.pedroso.upcomingmovies.network.services;

import java.util.List;

import br.pedroso.upcomingmovies.network.entities.RetrofitMovieEntity;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import io.reactivex.rxjava3.core.Observable;

public interface TheMovieDbService {
    String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/upcoming")
    Single<List<RetrofitMovieEntity>> listUpcomingMovies();

    @GET("movie/{movieId}")
    Single<RetrofitMovieEntity> getMovieDetails(@Path("movieId") Integer movieId);


    @GET("movie/{movieId}/similar")
    Single<List<RetrofitMovieEntity>> listSimilarMovies(@Path("movieId") Integer movieId);
}
