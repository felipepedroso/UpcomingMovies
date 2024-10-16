package br.pedroso.upcomingmovies.moviedetails

import br.pedroso.upcomingmovies.domain.Movie

sealed class MovieDetailsViewModelEvent {
    data class NavigateToMovieDetails(val movie: Movie) : MovieDetailsViewModelEvent()
    data object NavigateBack : MovieDetailsViewModelEvent()
}
