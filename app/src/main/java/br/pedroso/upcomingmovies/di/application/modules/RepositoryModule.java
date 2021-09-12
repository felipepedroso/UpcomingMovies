package br.pedroso.upcomingmovies.di.application.modules;

import javax.inject.Singleton;

import br.pedroso.upcomingmovies.shared.data.MoviesRepository;
import br.pedroso.upcomingmovies.shared.data.dataSource.DataSource;
import br.pedroso.upcomingmovies.shared.data.repository.MoviesRepositoryImpl;
import br.pedroso.upcomingmovies.shared.data.retrofit.RetrofitDataSource;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    DataSource provideDataSource(Retrofit retrofit) {
        return new RetrofitDataSource(retrofit);
    }

    @Singleton
    @Provides
    MoviesRepository provideMoviesRepository(DataSource dataSource) {
        return new MoviesRepositoryImpl(dataSource);
    }
}
