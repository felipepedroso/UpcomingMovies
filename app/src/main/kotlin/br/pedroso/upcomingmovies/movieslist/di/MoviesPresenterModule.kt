package br.pedroso.upcomingmovies.movieslist.di

import br.pedroso.upcomingmovies.movieslist.MoviesListView
import dagger.Module
import dagger.Provides

@Module
class MoviesPresenterModule(private val view: MoviesListView) {
    @Provides
    fun providesMoviesView(): MoviesListView {
        return view
    }
}
