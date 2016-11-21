package br.pedroso.movies;


import android.app.Application;

import com.ciandt.moviespoc.BuildConfig;

import br.pedroso.movies.data.retrofit.services.TheMovieDbService;
import br.pedroso.movies.di.components.DaggerRepositoryComponent;
import br.pedroso.movies.di.components.RepositoryComponent;
import br.pedroso.movies.di.modules.ApplicationModule;
import br.pedroso.movies.di.modules.NetworkModule;
import br.pedroso.movies.di.modules.RepositoryModule;

public class MoviesApplication extends Application {

    private RepositoryComponent repositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        createRepositoryComponent();
    }

    private void createRepositoryComponent() {
        repositoryComponent = DaggerRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(TheMovieDbService.BASE_URL, BuildConfig.MOVIES_DB_API_KEY))
                .repositoryModule(new RepositoryModule())
                .build();
    }

    public RepositoryComponent getRepositoryComponent() {
        return repositoryComponent;
    }

}
