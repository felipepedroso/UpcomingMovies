package br.pedroso.upcomingmovies.movieslist.fakes

import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MovieDetails
import br.pedroso.upcomingmovies.domain.MoviesRepository

class AlwaysSuccessfulUpcomingMoviesList(private val movies: List<Movie>) : MoviesRepository {
    override suspend fun listUpcomingMovies(): List<Movie> = movies

    override suspend fun getMovieDetails(movieId: Int): MovieDetails =
        error("This method shouldn't be used")
}
