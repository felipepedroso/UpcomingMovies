package br.pedroso.upcomingmovies.movieslist.fakes

import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MoviesRepository
import io.reactivex.rxjava3.core.Single

class AlwaysSuccessfulUpcomingMoviesList(private val movies: List<Movie>) : MoviesRepository {
    override suspend fun listUpcomingMovies(): List<Movie> = movies

    override suspend fun getMovieDetails(movieId: Int): Movie =
        error("This method shouldn't be used")

    override suspend fun listSimilarMovies(movieId: Int): List<Movie> =
        error("This method shouldn't be used")
}
