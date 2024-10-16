package br.pedroso.upcomingmovies.network.services

import br.pedroso.upcomingmovies.network.entities.RetrofitMovieEntity
import br.pedroso.upcomingmovies.network.entities.RetrofitResponseMoviesEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDbService {
    @GET("movie/upcoming")
    suspend fun listUpcomingMovies(): RetrofitResponseMoviesEntity

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int): RetrofitMovieEntity

    @GET("movie/{movieId}/similar")
    suspend fun listSimilarMovies(@Path("movieId") movieId: Int): RetrofitResponseMoviesEntity

    companion object {
        const val BASE_URL: String = "https://api.themoviedb.org/3/"
    }
}
