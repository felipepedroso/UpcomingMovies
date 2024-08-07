package br.pedroso.upcomingmovies.domain

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String? = null,
    val voteAverage: Double? = null,
    val releaseDate: String? = null,
    val overview: String? = null,
)
