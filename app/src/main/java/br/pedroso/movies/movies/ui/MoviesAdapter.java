package br.pedroso.movies.movies.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ciandt.moviespoc.R;
import br.pedroso.movies.shared.domain.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private final Context context;
    private List<Movie> moviesList;
    private OnMovieClickListener onMovieClickListener;

    public MoviesAdapter(Context context, OnMovieClickListener onMovieClickListener) {
        this.context = context;
        moviesList = new ArrayList<>(0);
        this.onMovieClickListener = onMovieClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = moviesList.get(position);

        holder.textViewMovieTitle.setText(movie.getTitle());

        // TODO: deal with null images
        Picasso.with(context).load(movie.getPosterPath()).into(holder.imageViewMoviePoster);

        holder.textViewMovieReleaseDate.setText(movie.getReleaseDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoviesAdapter.this.onMovieClickListener != null) {
                    onMovieClickListener.onMovieClick(movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void updateAdapterData(List<Movie> newMoviesList) {
        this.moviesList = newMoviesList;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewMoviePoster;
        public TextView textViewMovieTitle;
        public TextView textViewMovieReleaseDate;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewMoviePoster = (ImageView) itemView.findViewById(R.id.imageViewMoviePoster);
            textViewMovieTitle = (TextView) itemView.findViewById(R.id.textViewMovieTitle);
            textViewMovieReleaseDate = (TextView) itemView.findViewById(R.id.textViewMovieReleaseDate);
        }
    }

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
}
