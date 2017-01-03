package br.pedroso.movies.di.movies;

import br.pedroso.movies.shared.domain.model.Movie;
import br.pedroso.movies.shared.domain.repository.MoviesRepository;
import br.pedroso.movies.shared.domain.usecase.UseCase;
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
