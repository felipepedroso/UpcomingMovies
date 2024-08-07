package br.pedroso.upcomingmovies.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(var application: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }
}
