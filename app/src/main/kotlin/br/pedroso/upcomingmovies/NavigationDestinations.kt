package br.pedroso.upcomingmovies

import kotlinx.serialization.Serializable

@Serializable
object MoviesList

@Serializable
class MovieDetails(val movieId: Int)
