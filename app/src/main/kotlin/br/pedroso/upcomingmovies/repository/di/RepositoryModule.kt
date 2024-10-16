package br.pedroso.upcomingmovies.repository.di

import br.pedroso.upcomingmovies.domain.MoviesRepository
import br.pedroso.upcomingmovies.network.services.TheMovieDbService
import br.pedroso.upcomingmovies.repository.MoviesRepositoryImpl
import br.pedroso.upcomingmovies.repository.RetrofitMovieEntityToMovieMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideMoviesRepository(
        theMovieDbService: TheMovieDbService,
        retrofitMovieEntityToMovieMapper: RetrofitMovieEntityToMovieMapper,
    ): MoviesRepository {
        return MoviesRepositoryImpl(theMovieDbService, retrofitMovieEntityToMovieMapper)
    }
}
