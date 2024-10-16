package br.pedroso.upcomingmovies.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RetrofitResponseMoviesEntity(
    @SerialName("results") val results: List<RetrofitMovieEntity>,
)
