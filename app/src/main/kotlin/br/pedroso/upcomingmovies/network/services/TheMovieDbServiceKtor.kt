package br.pedroso.upcomingmovies.network.services

import br.pedroso.upcomingmovies.network.entities.NetworkMovie
import br.pedroso.upcomingmovies.network.entities.NetworkMoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TheMovieDbServiceKtor(private val client: HttpClient) : TheMovieDbService {
    override suspend fun listUpcomingMovies(): NetworkMoviesResponse {
        return client.get("movie/upcoming").body()
    }

    override suspend fun getMovieDetails(movieId: Int): NetworkMovie {
        return client.get("movie/${movieId}").body()
    }

    override suspend fun listSimilarMovies(movieId: Int): NetworkMoviesResponse {
        return client.get("movie/${movieId}/similar").body()
    }
}
