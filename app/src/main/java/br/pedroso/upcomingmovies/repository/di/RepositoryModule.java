package br.pedroso.upcomingmovies.repository.di;

import javax.inject.Singleton;

import br.pedroso.upcomingmovies.domain.MoviesRepository;
import br.pedroso.upcomingmovies.repository.MoviesRepositoryImpl;
import br.pedroso.upcomingmovies.network.services.TheMovieDbService;
import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    MoviesRepository provideMoviesRepository(TheMovieDbService theMovieDbService) {
        return new MoviesRepositoryImpl(theMovieDbService);
    }
}
