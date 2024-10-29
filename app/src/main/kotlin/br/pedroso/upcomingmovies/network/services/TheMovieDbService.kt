package br.pedroso.upcomingmovies.network.services

import br.pedroso.upcomingmovies.network.entities.NetworkMovie
import br.pedroso.upcomingmovies.network.entities.NetworkMoviesResponse

interface TheMovieDbService {
    suspend fun listUpcomingMovies(): NetworkMoviesResponse

    suspend fun getMovieDetails(movieId: Int): NetworkMovie

    suspend fun listSimilarMovies(movieId: Int): NetworkMoviesResponse

    companion object {
        const val BASE_URL: String = "https://api.themoviedb.org/3/"
    }
}
