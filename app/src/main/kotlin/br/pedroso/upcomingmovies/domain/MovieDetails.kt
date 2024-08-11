package br.pedroso.upcomingmovies.domain

data class MovieDetails(val movie: Movie, val similarMovies: List<Movie>)

