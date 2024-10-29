package br.pedroso.upcomingmovies.network.services

import br.pedroso.upcomingmovies.network.entities.NetworkMovie
import br.pedroso.upcomingmovies.network.entities.NetworkMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDbService {
    @GET("movie/upcoming")
    suspend fun listUpcomingMovies(): NetworkMoviesResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int): NetworkMovie

    @GET("movie/{movieId}/similar")
    suspend fun listSimilarMovies(@Path("movieId") movieId: Int): NetworkMoviesResponse

    companion object {
        const val BASE_URL: String = "https://api.themoviedb.org/3/"
    }
}
