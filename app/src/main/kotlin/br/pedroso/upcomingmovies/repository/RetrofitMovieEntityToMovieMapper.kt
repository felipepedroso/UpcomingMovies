package br.pedroso.upcomingmovies.repository

import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.network.entities.NetworkMovie
import javax.inject.Inject

class RetrofitMovieEntityToMovieMapper @Inject constructor() : (NetworkMovie) -> Movie {
    override fun invoke(networkMovie: NetworkMovie) = Movie(
        id = networkMovie.id,
        title = networkMovie.title,
        posterPath = IMAGE_TMDB_BASE_URL + networkMovie.posterPath,
        voteAverage = networkMovie.voteAverage / MAX_VOTE_AVERAGE,
        releaseDate = networkMovie.releaseDate,
        overview = networkMovie.overview,
    )

    private companion object {
        const val IMAGE_TMDB_BASE_URL = "https://image.tmdb.org/t/p/w500"

        const val MAX_VOTE_AVERAGE = 10.0
    }
}
