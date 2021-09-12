package br.pedroso.upcomingmovies.network.services;

import java.util.List;

import br.pedroso.upcomingmovies.network.entities.RetrofitMovieEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface TheMovieDbService {
    String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/upcoming")
    Observable<List<RetrofitMovieEntity>> listUpcomingMovies();

    @GET("movie/{movieId}")
    Observable<RetrofitMovieEntity> getMovieDetails(@Path("movieId") Integer movieId);


    @GET("movie/{movieId}/similar")
    Observable<List<RetrofitMovieEntity>> listSimilarMovies(@Path("movieId") Integer movieId);
}
