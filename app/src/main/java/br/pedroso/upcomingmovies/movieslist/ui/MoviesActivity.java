package br.pedroso.upcomingmovies.movieslist.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.MoviesApplication;
import br.pedroso.upcomingmovies.R;
import br.pedroso.upcomingmovies.di.ApplicationComponent;
import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.moviedetails.ui.MovieDetailsActivity;
import br.pedroso.upcomingmovies.movieslist.MoviesContract;
import br.pedroso.upcomingmovies.movieslist.di.DaggerMoviesComponent;
import br.pedroso.upcomingmovies.movieslist.di.MoviesPresenterModule;
import br.pedroso.upcomingmovies.movieslist.presenter.MoviesPresenter;

public class MoviesActivity extends AppCompatActivity implements MoviesContract.View, MoviesAdapter.OnMovieClickListener {

    private RecyclerView recyclerViewMovies;

    private MoviesAdapter moviesAdapter;

    @Inject
    MoviesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupView();

        injectPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause();
    }

    private void setupView() {
        setContentView(R.layout.activity_movies);

        setupRecyclerViewMovies();
    }

    private void setupRecyclerViewMovies() {
        recyclerViewMovies = (RecyclerView) findViewById(R.id.recyclerViewMovies);

        Context context = this;

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewMovies.setLayoutManager(layoutManager);

        moviesAdapter = new MoviesAdapter(context, this);
        recyclerViewMovies.setAdapter(moviesAdapter);
    }

    private void injectPresenter() {
        ApplicationComponent applicationComponent = ((MoviesApplication) getApplication()).getApplicationComponent();

        DaggerMoviesComponent.builder()
                .applicationComponent(applicationComponent)
                .moviesPresenterModule(new MoviesPresenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void renderMoviesList(List<Movie> moviesList) {
        moviesAdapter.updateAdapterData(moviesList);
    }

    @Override
    public void startMovieDetailsActivity(Integer movieId) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void cleanMoviesList() {
        moviesAdapter.clearItems();
    }

    @Override
    public void onMovieClick(Movie movie) {
        presenter.onMovieClick(movie);
    }
}
