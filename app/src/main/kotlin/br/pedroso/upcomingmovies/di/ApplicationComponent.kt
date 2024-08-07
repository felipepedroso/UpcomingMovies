package br.pedroso.upcomingmovies.di

import br.pedroso.upcomingmovies.domain.MoviesRepository
import br.pedroso.upcomingmovies.network.di.NetworkModule
import br.pedroso.upcomingmovies.repository.di.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, RepositoryModule::class])
interface ApplicationComponent {
    fun moviesRepository(): MoviesRepository
}
