package br.pedroso.movies;


import android.app.Application;

import br.pedroso.movies.di.application.ApplicationComponent;
import br.pedroso.movies.di.application.DaggerApplicationComponent;
import br.pedroso.movies.di.application.modules.ApplicationModule;
import br.pedroso.movies.di.application.modules.NetworkModule;
import br.pedroso.movies.di.application.modules.RepositoryModule;
import br.pedroso.movies.shared.data.retrofit.services.TheMovieDbService;

public class MoviesApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        createApplicationComponent();
    }

    private void createApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(TheMovieDbService.BASE_URL, BuildConfig.MOVIES_DB_API_KEY))
                .repositoryModule(new RepositoryModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
