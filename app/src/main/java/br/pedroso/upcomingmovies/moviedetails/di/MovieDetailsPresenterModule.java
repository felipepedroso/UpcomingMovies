package br.pedroso.upcomingmovies.moviedetails.di;

import br.pedroso.upcomingmovies.moviedetails.MovieDetailsContract;
import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailsPresenterModule {
    private final MovieDetailsContract.View view;

    public MovieDetailsPresenterModule(MovieDetailsContract.View view) {
        this.view = view;
    }

    @Provides
    MovieDetailsContract.View provideMovieDetailsView() {
        return view;
    }
}
