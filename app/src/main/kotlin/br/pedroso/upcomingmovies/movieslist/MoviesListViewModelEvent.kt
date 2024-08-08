package br.pedroso.upcomingmovies.movieslist

import br.pedroso.upcomingmovies.domain.Movie

sealed class MoviesListViewModelEvent {
    data class NavigateToMovieDetails(val movie: Movie) : MoviesListViewModelEvent()
}
