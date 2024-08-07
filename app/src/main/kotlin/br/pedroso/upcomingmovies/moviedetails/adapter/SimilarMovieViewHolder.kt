package br.pedroso.upcomingmovies.moviedetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.upcomingmovies.databinding.ItemSimilarMovieBinding
import br.pedroso.upcomingmovies.domain.Movie
import com.squareup.picasso.Picasso

class SimilarMovieViewHolder private constructor(
    private val binding: ItemSimilarMovieBinding,
    private val onSimilarMovieClicked: OnSimilarMovieClicked
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) {
        Picasso.with(binding.root.context).load(movie.posterPath).into(binding.imageViewMoviePoster)

        binding.root.setOnClickListener { v: View? -> onSimilarMovieClicked.onMovieClick(movie) }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onSimilarMovieClicked: OnSimilarMovieClicked
        ): SimilarMovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)

            val binding = ItemSimilarMovieBinding.inflate(inflater, parent, false)

            return SimilarMovieViewHolder(binding, onSimilarMovieClicked)
        }
    }
}
