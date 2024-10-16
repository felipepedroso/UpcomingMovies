package br.pedroso.upcomingmovies.moviedetails

import br.pedroso.upcomingmovies.domain.Movie

sealed class MovieDetailsUiEvent {
    data object ClickedOnNavigateBack : MovieDetailsUiEvent()
    data class ClickedOnSimilarMovie(val movie: Movie) : MovieDetailsUiEvent()
}
