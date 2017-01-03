package br.pedroso.movies.di.movieDetails;

import br.pedroso.movies.shared.domain.model.Movie;
import br.pedroso.movies.shared.domain.repository.MoviesRepository;
import br.pedroso.movies.shared.domain.usecase.UseCase;
import br.pedroso.movies.movieDetails.MovieDetailsContract;
import br.pedroso.movies.movieDetails.usecases.GetMovieDetails;
import br.pedroso.movies.movieDetails.usecases.ListSimilarMovies;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailsPresenterModule {
    private Integer movieToBePresentedId;
    private MovieDetailsContract.View view;

    public MovieDetailsPresenterModule(Integer movieToBePresentedId, MovieDetailsContract.View view) {
        this.movieToBePresentedId = movieToBePresentedId;
        this.view = view;
    }

    @Provides
    MovieDetailsContract.View provideMovieDetailsView() {
        return view;
    }

    @Provides
    UseCase<Movie> provideGetMovieDetailsUseCase(MoviesRepository moviesRepository) {
        return new GetMovieDetails(movieToBePresentedId, moviesRepository);
    }

    @Provides
    UseCase<List<Movie>> provideListSimilarMoviesUseCase(MoviesRepository moviesRepository) {
        return new ListSimilarMovies(movieToBePresentedId, moviesRepository);
    }
}
