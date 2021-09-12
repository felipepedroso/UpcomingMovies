package br.pedroso.upcomingmovies.movieslist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.MoviesApplication;
import br.pedroso.upcomingmovies.databinding.ActivityMoviesBinding;
import br.pedroso.upcomingmovies.di.ApplicationComponent;
import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.moviedetails.ui.MovieDetailsActivity;
import br.pedroso.upcomingmovies.movieslist.adapter.MoviesAdapter;
import br.pedroso.upcomingmovies.movieslist.di.DaggerMoviesComponent;
import br.pedroso.upcomingmovies.movieslist.di.MoviesPresenterModule;

public class MoviesListActivity extends AppCompatActivity implements MoviesListView {

    private MoviesAdapter moviesAdapter;

    private ActivityMoviesBinding binding;

    @Inject
    MoviesListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectPresenter();
        setupView();
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
        binding = ActivityMoviesBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setupRecyclerViewMovies();
    }

    private void setupRecyclerViewMovies() {
        moviesAdapter = new MoviesAdapter(presenter::onMovieClick);
        binding.recyclerViewMovies.setAdapter(moviesAdapter);
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
}
