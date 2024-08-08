package br.pedroso.upcomingmovies.movieslist

import br.pedroso.upcomingmovies.domain.Movie

sealed class MoviesListUiState {
    data object Loading : MoviesListUiState()
    data object Empty : MoviesListUiState()
    data class Error(val cause: Throwable) : MoviesListUiState()
    data class DisplayMovies(val movies: List<Movie>) : MoviesListUiState()
}
