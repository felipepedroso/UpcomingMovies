package br.pedroso.upcomingmovies.movieslist.fakes

import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MoviesRepository
import io.reactivex.rxjava3.core.Single

class AlwaysFailingUpcomingMoviesList() : MoviesRepository {
    override suspend fun listUpcomingMovies(): List<Movie> = throw defaultException

    override fun getMovieDetails(movieId: Int?): Single<Movie> =
        error("This method shouldn't be used")

    override fun listSimilarMovies(movieId: Int?): Single<List<Movie>> =
        error("This method shouldn't be used")

    companion object {
        val defaultException = Throwable("Failed to fetch the movies.")
    }
}
