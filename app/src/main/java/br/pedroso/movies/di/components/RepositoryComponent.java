package br.pedroso.movies.di.components;

import br.pedroso.movies.di.modules.ApplicationModule;
import br.pedroso.movies.di.modules.NetworkModule;
import br.pedroso.movies.di.modules.RepositoryModule;
import br.pedroso.movies.domain.repository.MoviesRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, RepositoryModule.class})
public interface RepositoryComponent {
    MoviesRepository moviesRepository();
}
