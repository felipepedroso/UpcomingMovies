package br.pedroso.upcomingmovies.movieslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.upcomingmovies.databinding.ItemMovieBinding
import br.pedroso.upcomingmovies.domain.Movie
import com.squareup.picasso.Picasso

class MovieViewHolder private constructor(
    private val binding: ItemMovieBinding,
    private val onMovieClickListener: OnMovieClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.textViewMovieTitle.text = movie.title

        // TODO: deal with null images
        Picasso.with(binding.root.context).load(movie.posterPath).into(binding.imageViewMoviePoster)

        binding.textViewMovieReleaseDate.text = movie.releaseDate

        binding.root.setOnClickListener {
            onMovieClickListener.onMovieClick(movie)
        }
    }

    companion object {
        @JvmStatic
        fun create(parent: ViewGroup, onMovieClickListener: OnMovieClickListener): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)

            val binding = ItemMovieBinding.inflate(inflater, parent, false)

            return MovieViewHolder(binding, onMovieClickListener)
        }
    }
}
