package br.pedroso.upcomingmovies.moviedetails.fakes

import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MovieDetails
import br.pedroso.upcomingmovies.domain.MoviesRepository

class AlwaysFailingGetMovieDetails : MoviesRepository {
    override suspend fun listUpcomingMovies(): List<Movie> = error("This method shouldn't be used")

    override suspend fun getMovieDetails(movieId: Int): MovieDetails = throw defaultException

    companion object {
        val defaultException = Throwable("Failed to fetch movie details")
    }
}
