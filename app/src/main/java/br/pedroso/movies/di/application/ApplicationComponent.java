package br.pedroso.movies.di.application;

import br.pedroso.movies.di.application.modules.ApplicationModule;
import br.pedroso.movies.di.application.modules.NetworkModule;
import br.pedroso.movies.di.application.modules.RepositoryModule;
import br.pedroso.movies.shared.data.MoviesRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, RepositoryModule.class})
public interface ApplicationComponent {
    MoviesRepository moviesRepository();
}
