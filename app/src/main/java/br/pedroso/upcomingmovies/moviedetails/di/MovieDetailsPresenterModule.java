package br.pedroso.upcomingmovies.moviedetails.di;

import br.pedroso.upcomingmovies.moviedetails.MovieDetailsView;
import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailsPresenterModule {
    private final MovieDetailsView view;

    public MovieDetailsPresenterModule(MovieDetailsView view) {
        this.view = view;
    }

    @Provides
    MovieDetailsView provideMovieDetailsView() {
        return view;
    }
}
