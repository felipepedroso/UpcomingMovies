package br.pedroso.upcomingmovies.movieslist.adapter

import br.pedroso.upcomingmovies.domain.Movie

fun interface OnMovieClickListener {
    fun onMovieClick(movie: Movie)
}
