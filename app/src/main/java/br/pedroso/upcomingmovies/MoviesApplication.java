package br.pedroso.upcomingmovies;


import android.app.Application;

import br.pedroso.upcomingmovies.di.application.ApplicationComponent;
import br.pedroso.upcomingmovies.di.application.DaggerApplicationComponent;
import br.pedroso.upcomingmovies.di.application.modules.ApplicationModule;
import br.pedroso.upcomingmovies.di.application.modules.NetworkModule;
import br.pedroso.upcomingmovies.di.application.modules.RepositoryModule;
import br.pedroso.upcomingmovies.network.services.TheMovieDbService;

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
