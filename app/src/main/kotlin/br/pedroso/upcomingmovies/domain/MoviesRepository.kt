package br.pedroso.upcomingmovies.domain

interface MoviesRepository {
    suspend fun listUpcomingMovies(): List<Movie>

    suspend fun getMovieDetails(movieId: Int): Movie

    suspend fun listSimilarMovies(movieId: Int): List<Movie>
}
