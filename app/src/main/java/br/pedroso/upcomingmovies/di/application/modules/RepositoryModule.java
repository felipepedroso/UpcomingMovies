package br.pedroso.upcomingmovies.di.application.modules;

import javax.inject.Singleton;

import br.pedroso.upcomingmovies.domain.MoviesRepository;
import br.pedroso.upcomingmovies.shared.data.repository.MoviesRepositoryImpl;
import br.pedroso.upcomingmovies.retrofit.services.TheMovieDbService;
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
