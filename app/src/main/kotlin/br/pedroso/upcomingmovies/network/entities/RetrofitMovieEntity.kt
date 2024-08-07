package br.pedroso.upcomingmovies.network.entities

import com.google.gson.annotations.SerializedName

class RetrofitMovieEntity(
    val id: Int,
    @SerializedName("poster_path") val posterPath: String,
    val title: String,
    @SerializedName("vote_average") var voteAverage: Double,
    @SerializedName("release_date") var releaseDate: String,
    var overview: String
)
