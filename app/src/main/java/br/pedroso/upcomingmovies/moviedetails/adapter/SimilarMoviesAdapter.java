package br.pedroso.upcomingmovies.moviedetails.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.pedroso.upcomingmovies.R;
import br.pedroso.upcomingmovies.domain.Movie;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder> {
    private final Context context;
    private List<Movie> moviesList;

    public SimilarMoviesAdapter(Context context) {
        this.context = context;
        moviesList = new ArrayList<>(0);
    }

    @Override
    public SimilarMoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_similar_movie, parent, false);

        SimilarMoviesAdapter.ViewHolder viewHolder = new SimilarMoviesAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SimilarMoviesAdapter.ViewHolder holder, int position) {
        final Movie movie = moviesList.get(position);

        // TODO: deal with null images
        Picasso.with(context).load(movie.getPosterPath()).into(holder.imageViewMoviePoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: implement click
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

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewMoviePoster = (ImageView) itemView.findViewById(R.id.imageViewMoviePoster);
        }
    }
}
