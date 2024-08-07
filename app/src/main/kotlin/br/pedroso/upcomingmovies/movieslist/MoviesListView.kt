package br.pedroso.upcomingmovies.movieslist

import br.pedroso.upcomingmovies.domain.Movie

interface MoviesListView {
    fun renderMoviesList(moviesList: List<Movie>)

    fun startMovieDetailsActivity(movieId: Int)

    fun cleanMoviesList()
}
