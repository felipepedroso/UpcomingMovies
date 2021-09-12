package br.pedroso.movies.di.application;

import javax.inject.Singleton;

import br.pedroso.movies.di.application.modules.ApplicationModule;
import br.pedroso.movies.di.application.modules.NetworkModule;
import br.pedroso.movies.di.application.modules.RepositoryModule;
import br.pedroso.movies.shared.data.MoviesRepository;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, RepositoryModule.class})
public interface ApplicationComponent {
    MoviesRepository moviesRepository();
}
