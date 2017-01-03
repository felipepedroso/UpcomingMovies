package br.pedroso.movies.di.application.modules;

import br.pedroso.movies.shared.data.dataSource.DataSource;
import br.pedroso.movies.shared.data.repository.MoviesRepositoryImpl;
import br.pedroso.movies.shared.data.retrofit.RetrofitDataSource;
import br.pedroso.movies.shared.domain.repository.MoviesRepository;

import javax.inject.Singleton;

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
