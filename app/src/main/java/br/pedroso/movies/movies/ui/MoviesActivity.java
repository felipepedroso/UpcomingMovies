package br.pedroso.movies.movies.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.pedroso.movies.MoviesApplication;
import com.ciandt.moviespoc.R;
import br.pedroso.movies.di.components.RepositoryComponent;
import br.pedroso.movies.movies.di.DaggerMoviesComponent;
import br.pedroso.movies.movies.di.MoviesPresenterModule;
import br.pedroso.movies.movies.presenter.MoviesPresenter;
import br.pedroso.movies.utils.FragmentUtils;

import javax.inject.Inject;

public class MoviesActivity extends AppCompatActivity {

    @Inject
    MoviesPresenter presenter;

    private MoviesFragment moviesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movies);

        createMoviesFragment();

        injectPresenter();
    }

    private void createMoviesFragment() {
        moviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (moviesFragment == null) {
            moviesFragment = MoviesFragment.newInstance();
            FragmentUtils.addFragmentToActivity(getSupportFragmentManager(), moviesFragment, R.id.contentFrame);
        }
    }

    private void injectPresenter() {
        RepositoryComponent repositoryComponent = ((MoviesApplication) getApplication()).getRepositoryComponent();

        DaggerMoviesComponent.builder()
                .repositoryComponent(repositoryComponent)
                .moviesPresenterModule(new MoviesPresenterModule(moviesFragment))
                .build()
                .inject(this);
    }
}
