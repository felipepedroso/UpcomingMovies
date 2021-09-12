package br.pedroso.upcomingmovies.di.application;

import javax.inject.Singleton;

import br.pedroso.upcomingmovies.di.application.modules.ApplicationModule;
import br.pedroso.upcomingmovies.di.application.modules.NetworkModule;
import br.pedroso.upcomingmovies.di.application.modules.RepositoryModule;
import br.pedroso.upcomingmovies.shared.data.MoviesRepository;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, RepositoryModule.class})
public interface ApplicationComponent {
    MoviesRepository moviesRepository();
}
