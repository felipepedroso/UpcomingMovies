package br.pedroso.upcomingmovies.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RetrofitMovieEntity(
    @SerialName("id") val id: Int,
    @SerialName("poster_path") val posterPath: String = "",
    @SerialName("title") val title: String,
    @SerialName("vote_average") var voteAverage: Double,
    @SerialName("release_date") var releaseDate: String,
    @SerialName("overview") var overview: String,
)
