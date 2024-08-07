package br.pedroso.upcomingmovies.moviedetails.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.upcomingmovies.domain.Movie

class SimilarMoviesAdapter(private val onSimilarMovieClicked: OnSimilarMovieClicked) :
    RecyclerView.Adapter<SimilarMovieViewHolder>() {
    private var moviesList: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieViewHolder {
        return SimilarMovieViewHolder.create(parent, onSimilarMovieClicked)
    }

    override fun onBindViewHolder(holder: SimilarMovieViewHolder, position: Int) {
        val movie = moviesList[position]

        holder.bind(movie)
    }

    override fun getItemCount(): Int = moviesList.size

    fun updateAdapterData(newMoviesList: List<Movie>) {
        moviesList = newMoviesList
        notifyDataSetChanged()
    }
}
