package br.pedroso.upcomingmovies.movieslist.fakes

import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MoviesRepository

class AlwaysFailingUpcomingMoviesList() : MoviesRepository {
    override suspend fun listUpcomingMovies(): List<Movie> = throw defaultException

    override suspend fun getMovieDetails(movieId: Int): Movie =
        error("This method shouldn't be used")

    override suspend fun listSimilarMovies(movieId: Int): List<Movie> =
        error("This method shouldn't be used")

    companion object {
        val defaultException = Throwable("Failed to fetch the movies.")
    }
}
