package br.pedroso.upcomingmovies.repository

import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.network.entities.RetrofitMovieEntity
import javax.inject.Inject

class RetrofitMovieEntityToMovieMapper @Inject constructor() : (RetrofitMovieEntity) -> Movie {
    override fun invoke(retrofitMovieEntity: RetrofitMovieEntity) = Movie(
        id = retrofitMovieEntity.id,
        title = retrofitMovieEntity.title,
        posterPath = IMAGE_TMDB_BASE_URL + retrofitMovieEntity.posterPath,
        voteAverage = retrofitMovieEntity.voteAverage / MAX_VOTE_AVERAGE,
        releaseDate = retrofitMovieEntity.releaseDate,
        overview = retrofitMovieEntity.overview,
    )

    private companion object {
        const val IMAGE_TMDB_BASE_URL = "https://image.tmdb.org/t/p/w500"

        const val MAX_VOTE_AVERAGE = 10.0
    }
}
