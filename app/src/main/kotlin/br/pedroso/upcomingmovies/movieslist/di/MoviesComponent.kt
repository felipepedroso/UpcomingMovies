package br.pedroso.upcomingmovies.movieslist.di

import br.pedroso.upcomingmovies.di.ApplicationComponent
import br.pedroso.upcomingmovies.di.scopes.FragmentScope
import br.pedroso.upcomingmovies.movieslist.MoviesListActivity
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [MoviesPresenterModule::class])
interface MoviesComponent {
    fun inject(activity: MoviesListActivity)
}
