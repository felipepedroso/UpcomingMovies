package br.pedroso.upcomingmovies.network.services

import br.pedroso.upcomingmovies.network.entities.RetrofitMovieEntity
import br.pedroso.upcomingmovies.network.entities.RetrofitResponseMoviesEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDbService {
    @GET("movie/upcoming")
    fun listUpcomingMovies(): Single<RetrofitResponseMoviesEntity>

    @GET("movie/{movieId}")
    fun getMovieDetails(@Path("movieId") movieId: Int?): Single<RetrofitMovieEntity>


    @GET("movie/{movieId}/similar")
    fun listSimilarMovies(@Path("movieId") movieId: Int?): Single<RetrofitResponseMoviesEntity>

    companion object {
        const val BASE_URL: String = "https://api.themoviedb.org/3/"
    }
}
