package br.pedroso.upcomingmovies.di;

import javax.inject.Singleton;

import br.pedroso.upcomingmovies.network.di.NetworkModule;
import br.pedroso.upcomingmovies.repository.di.RepositoryModule;
import br.pedroso.upcomingmovies.domain.MoviesRepository;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, RepositoryModule.class})
public interface ApplicationComponent {
    MoviesRepository moviesRepository();
}
