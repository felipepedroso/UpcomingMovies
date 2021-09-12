package br.pedroso.upcomingmovies.movieslist.di;

import br.pedroso.upcomingmovies.movieslist.MoviesContract;
import dagger.Module;
import dagger.Provides;

@Module
public class MoviesPresenterModule {
    private final MoviesContract.View view;

    public MoviesPresenterModule(MoviesContract.View view) {
        this.view = view;
    }

    @Provides
    MoviesContract.View providesMoviesView() {
        return view;
    }
}
