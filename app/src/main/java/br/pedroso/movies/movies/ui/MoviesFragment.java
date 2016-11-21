package br.pedroso.movies.movies.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ciandt.moviespoc.R;
import br.pedroso.movies.domain.model.Movie;
import br.pedroso.movies.movieDetails.ui.MovieDetailsActivity;
import br.pedroso.movies.movies.MoviesContract;

import java.util.List;

public class MoviesFragment extends Fragment implements MoviesContract.View, MoviesAdapter.OnMovieClickListener {
    private MoviesContract.Presenter presenter;
    private RecyclerView recyclerViewMovies;
    private MoviesAdapter moviesAdapter;

    public MoviesFragment() {
    }

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        setupRecyclerViewMovies(view);

        return view;
    }

    private void setupRecyclerViewMovies(View view) {
        recyclerViewMovies = (RecyclerView) view.findViewById(R.id.recyclerView_movies);

        Context context = getContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewMovies.setLayoutManager(layoutManager);

        moviesAdapter = new MoviesAdapter(context, this);
        recyclerViewMovies.setAdapter(moviesAdapter);
    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void renderMoviesList(List<Movie> moviesList) {
        moviesAdapter.updateAdapterData(moviesList);
    }

    @Override
    public void startMovieDetailsActivity(Integer movieId) {
        Intent intent = new Intent(this.getActivity(), MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void onMovieClick(Movie movie) {
        presenter.onMovieClick(movie);
    }
}
