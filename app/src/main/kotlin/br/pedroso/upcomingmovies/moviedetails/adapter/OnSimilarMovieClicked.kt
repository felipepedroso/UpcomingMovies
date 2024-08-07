package br.pedroso.upcomingmovies.moviedetails.adapter

import br.pedroso.upcomingmovies.domain.Movie

fun interface OnSimilarMovieClicked {
    fun onMovieClick(movie: Movie)
}
