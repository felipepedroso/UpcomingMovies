package br.pedroso.movies.di.movies;

import br.pedroso.movies.movies.MoviesContract;
import br.pedroso.movies.movies.usecases.ListUpcomingMovies;
import br.pedroso.movies.shared.data.MoviesRepository;
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
