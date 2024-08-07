package br.pedroso.upcomingmovies.moviedetails.di

import br.pedroso.upcomingmovies.moviedetails.MovieDetailsView
import dagger.Module
import dagger.Provides

@Module
class MovieDetailsPresenterModule(private val view: MovieDetailsView) {
    @Provides
    fun provideMovieDetailsView(): MovieDetailsView {
        return view
    }
}
