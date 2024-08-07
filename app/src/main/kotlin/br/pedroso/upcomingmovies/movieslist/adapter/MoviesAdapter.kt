package br.pedroso.upcomingmovies.movieslist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.movieslist.adapter.MovieViewHolder.Companion.create

class MoviesAdapter(private val onMovieClickListener: OnMovieClickListener) :
    RecyclerView.Adapter<MovieViewHolder>() {
    private var moviesList = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return create(parent, onMovieClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]

        holder.bind(movie)
    }

    override fun getItemCount(): Int = moviesList.size

    fun updateAdapterData(newMoviesList: List<Movie>) {
        moviesList.clear()
        moviesList += newMoviesList
        notifyDataSetChanged()
    }

    fun clearItems() {
        moviesList.clear()
        notifyDataSetChanged()
    }
}
