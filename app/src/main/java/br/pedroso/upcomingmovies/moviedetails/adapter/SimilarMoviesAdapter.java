package br.pedroso.upcomingmovies.moviedetails.adapter;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.pedroso.upcomingmovies.domain.Movie;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMovieViewHolder> {
    private final OnSimilarMovieClicked onSimilarMovieClicked;
    private List<Movie> moviesList;

    public SimilarMoviesAdapter(OnSimilarMovieClicked onSimilarMovieClicked) {
        this.onSimilarMovieClicked = onSimilarMovieClicked;
        moviesList = new ArrayList<>(0);
    }

    @Override
    public SimilarMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SimilarMovieViewHolder.create(parent, onSimilarMovieClicked);
    }

    @Override
    public void onBindViewHolder(SimilarMovieViewHolder holder, int position) {
        final Movie movie = moviesList.get(position);

        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void updateAdapterData(List<Movie> newMoviesList) {
        this.moviesList = newMoviesList;
        this.notifyDataSetChanged();
    }
}
