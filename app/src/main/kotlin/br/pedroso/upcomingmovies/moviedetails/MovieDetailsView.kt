package br.pedroso.upcomingmovies.moviedetails

import br.pedroso.upcomingmovies.domain.Movie

interface MovieDetailsView {
    fun renderMovieDetails(movie: Movie)

    fun hideSimilarMoviesPanel()

    fun renderSimilarMovies(movies: List<Movie>)

    fun displaySimilarMoviesPanel()

    fun startMovieDetailsActivity(id: Int)
}
