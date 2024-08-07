package br.pedroso.upcomingmovies

import android.app.Application
import br.pedroso.upcomingmovies.di.ApplicationComponent
import br.pedroso.upcomingmovies.di.ApplicationModule
import br.pedroso.upcomingmovies.di.DaggerApplicationComponent
import br.pedroso.upcomingmovies.network.di.NetworkModule
import br.pedroso.upcomingmovies.repository.di.RepositoryModule


class MoviesApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        createApplicationComponent()
    }

    private fun createApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .networkModule(NetworkModule(BuildConfig.MOVIES_DB_API_KEY))
            .repositoryModule(RepositoryModule())
            .build()
    }
}
