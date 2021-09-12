package br.pedroso.upcomingmovies;


import android.app.Application;

import br.pedroso.upcomingmovies.di.ApplicationComponent;
import br.pedroso.upcomingmovies.di.ApplicationModule;
import br.pedroso.upcomingmovies.di.DaggerApplicationComponent;
import br.pedroso.upcomingmovies.network.di.NetworkModule;
import br.pedroso.upcomingmovies.repository.di.RepositoryModule;

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
                .networkModule(new NetworkModule(BuildConfig.MOVIES_DB_API_KEY))
                .repositoryModule(new RepositoryModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
