package br.pedroso.upcomingmovies.moviedetails.fakes

import br.pedroso.upcomingmovies.domain.GetMovieDetails
import br.pedroso.upcomingmovies.domain.MovieDetails

class AlwaysSuccessfulMovieDetails(
    private val movieDetails: MovieDetails
) : GetMovieDetails {
    override suspend fun invoke(movieId: Int): MovieDetails = movieDetails
}
