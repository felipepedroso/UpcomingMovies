package br.pedroso.upcomingmovies.domain

import io.reactivex.rxjava3.core.Single

interface MoviesRepository {
    suspend fun listUpcomingMovies(): List<Movie>

    fun getMovieDetails(movieId: Int?): Single<Movie>

    fun listSimilarMovies(movieId: Int?): Single<List<Movie>>
}
