package br.pedroso.upcomingmovies.domain

interface GetMovieDetails : suspend (Int) -> MovieDetails {
    override suspend fun invoke(movieId: Int): MovieDetails
}
