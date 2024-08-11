package br.pedroso.upcomingmovies.moviedetails.fakes

import br.pedroso.upcomingmovies.domain.GetMovieDetails
import br.pedroso.upcomingmovies.domain.MovieDetails

class AlwaysFailingMovieDetails() : GetMovieDetails {
    override suspend fun invoke(movieId: Int): MovieDetails = throw defaultException

    companion object {
        val defaultException = Throwable("Failed to fetch movie details")
    }
}
