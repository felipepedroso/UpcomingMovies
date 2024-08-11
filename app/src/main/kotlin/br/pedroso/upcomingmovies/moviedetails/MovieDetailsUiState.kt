package br.pedroso.upcomingmovies.moviedetails

import br.pedroso.upcomingmovies.domain.MovieDetails

sealed class MovieDetailsUiState {
    data class DisplayMovieDetails(val movieDetails: MovieDetails) : MovieDetailsUiState()
    data object Loading : MovieDetailsUiState()
    data class Error(val cause: Throwable) : MovieDetailsUiState()
}
