package br.pedroso.movies.movies.di;

import br.pedroso.movies.domain.model.Movie;
import br.pedroso.movies.domain.repository.MoviesRepository;
import br.pedroso.movies.domain.usecase.UseCase;
import br.pedroso.movies.movies.MoviesContract;
import br.pedroso.movies.movies.usecases.ListUpcomingMovies;

import java.util.List;

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
    UseCase<List<Movie>> provideListUpcomingMoviesUseCase(MoviesRepository moviesRepository) {
        return new ListUpcomingMovies(moviesRepository);
    }
}
