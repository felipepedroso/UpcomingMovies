package br.pedroso.movies.movies.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ciandt.moviespoc.R;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.movies.MoviesApplication;
import br.pedroso.movies.di.application.ApplicationComponent;
import br.pedroso.movies.di.movies.DaggerMoviesComponent;
import br.pedroso.movies.di.movies.MoviesPresenterModule;
import br.pedroso.movies.movieDetails.ui.MovieDetailsActivity;
import br.pedroso.movies.movies.MoviesContract;
import br.pedroso.movies.movies.presenter.MoviesPresenter;
import br.pedroso.movies.shared.domain.model.Movie;

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
    public void onMovieClick(Movie movie) {
        presenter.onMovieClick(movie);
    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        // We don't need to set the presenter, since it's being injected by Dagger
    }
}
