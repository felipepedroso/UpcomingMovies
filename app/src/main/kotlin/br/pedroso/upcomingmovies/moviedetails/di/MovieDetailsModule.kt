package br.pedroso.upcomingmovies.moviedetails.di

import br.pedroso.upcomingmovies.domain.GetMovieDetails
import br.pedroso.upcomingmovies.moviedetails.GetMovieDetailsImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MovieDetailsModule {
    @Binds
    abstract fun provideGetMovieDetails(getMovieDetailsImpl: GetMovieDetailsImpl): GetMovieDetails
}
