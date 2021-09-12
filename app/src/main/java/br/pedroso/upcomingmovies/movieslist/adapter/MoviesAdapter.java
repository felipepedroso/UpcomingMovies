package br.pedroso.upcomingmovies.movieslist.adapter;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.pedroso.upcomingmovies.domain.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private List<Movie> moviesList;
    private OnMovieClickListener onMovieClickListener;

    public MoviesAdapter(OnMovieClickListener onMovieClickListener) {
        moviesList = new ArrayList<>(0);
        this.onMovieClickListener = onMovieClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MovieViewHolder.create(parent, onMovieClickListener);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
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

    public void clearItems() {
        this.moviesList.clear();
        this.notifyDataSetChanged();
    }
}
