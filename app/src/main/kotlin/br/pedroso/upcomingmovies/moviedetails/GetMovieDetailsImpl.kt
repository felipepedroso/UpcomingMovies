package br.pedroso.upcomingmovies.moviedetails

import br.pedroso.upcomingmovies.domain.GetMovieDetails
import br.pedroso.upcomingmovies.domain.MovieDetails
import br.pedroso.upcomingmovies.domain.MoviesRepository
import javax.inject.Inject

class GetMovieDetailsImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetMovieDetails {

    override suspend fun invoke(movieId: Int): MovieDetails {
        return MovieDetails(
            movie = moviesRepository.getMovieDetails(movieId),
            similarMovies = moviesRepository.listSimilarMovies(movieId),
        )
    }
}
