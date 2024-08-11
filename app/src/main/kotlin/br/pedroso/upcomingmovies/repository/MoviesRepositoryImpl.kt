package br.pedroso.upcomingmovies.repository

import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MoviesRepository
import br.pedroso.upcomingmovies.network.services.TheMovieDbService
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val theMovieDbService: TheMovieDbService,
    private val retrofitMovieEntityToMovieMapper: RetrofitMovieEntityToMovieMapper,
) : MoviesRepository {
    override suspend fun listUpcomingMovies(): List<Movie> {
        return theMovieDbService.listUpcomingMovies().results.map(retrofitMovieEntityToMovieMapper)
    }

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return retrofitMovieEntityToMovieMapper(theMovieDbService.getMovieDetails(movieId))
    }

    override suspend fun listSimilarMovies(movieId: Int): List<Movie> {
        return theMovieDbService.listSimilarMovies(movieId).results.map(
            retrofitMovieEntityToMovieMapper
        )
    }
}
