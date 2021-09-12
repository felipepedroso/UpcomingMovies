package br.pedroso.upcomingmovies.movieslist.di;

import br.pedroso.upcomingmovies.movieslist.MoviesListView;
import dagger.Module;
import dagger.Provides;

@Module
public class MoviesPresenterModule {
    private final MoviesListView view;

    public MoviesPresenterModule(MoviesListView view) {
        this.view = view;
    }

    @Provides
    MoviesListView providesMoviesView() {
        return view;
    }
}
