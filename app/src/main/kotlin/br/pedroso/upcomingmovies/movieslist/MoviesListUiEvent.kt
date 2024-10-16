package br.pedroso.upcomingmovies.movieslist

import br.pedroso.upcomingmovies.domain.Movie

sealed class MoviesListUiEvent {
    data class ClickedOnMovie(val movie: Movie) : MoviesListUiEvent()
}
