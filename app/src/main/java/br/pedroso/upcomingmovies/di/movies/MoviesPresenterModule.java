package br.pedroso.upcomingmovies.di.movies;

import br.pedroso.upcomingmovies.movieslist.MoviesContract;
import br.pedroso.upcomingmovies.movieslist.usecases.ListUpcomingMovies;
import br.pedroso.upcomingmovies.domain.MoviesRepository;
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

    @Provides
    ListUpcomingMovies provideListUpcomingMoviesUseCase(MoviesRepository moviesRepository) {
        return new ListUpcomingMovies(moviesRepository);
    }
}
