package br.pedroso.upcomingmovies.moviedetails.di

import br.pedroso.upcomingmovies.di.ApplicationComponent
import br.pedroso.upcomingmovies.di.scopes.FragmentScope
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsActivity
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [MovieDetailsModule::class]
)
interface MovieDetailsComponent {
    fun inject(activity: MovieDetailsActivity)
}
