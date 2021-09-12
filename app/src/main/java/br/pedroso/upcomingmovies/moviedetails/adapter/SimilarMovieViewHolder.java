package br.pedroso.upcomingmovies.moviedetails.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import br.pedroso.upcomingmovies.databinding.ItemSimilarMovieBinding;
import br.pedroso.upcomingmovies.domain.Movie;

public class SimilarMovieViewHolder extends RecyclerView.ViewHolder {
    private ItemSimilarMovieBinding binding;
    private OnSimilarMovieClicked onSimilarMovieClicked;

    private SimilarMovieViewHolder(ItemSimilarMovieBinding binding, OnSimilarMovieClicked onSimilarMovieClicked) {
        super(binding.getRoot());
        this.binding = binding;
        this.onSimilarMovieClicked = onSimilarMovieClicked;
    }

    public void bind(Movie movie) {
        Picasso.with(binding.getRoot().getContext()).load(movie.getPosterPath()).into(binding.imageViewMoviePoster);

        binding.getRoot().setOnClickListener(v -> onSimilarMovieClicked.onMovieClick(movie));
    }

    public static SimilarMovieViewHolder create(ViewGroup parent, OnSimilarMovieClicked onSimilarMovieClicked) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ItemSimilarMovieBinding binding = ItemSimilarMovieBinding.inflate(inflater, parent, false);

        return new SimilarMovieViewHolder(binding, onSimilarMovieClicked);
    }
}
