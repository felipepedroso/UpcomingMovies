package br.pedroso.movies.di.movieDetails;

import br.pedroso.movies.movieDetails.MovieDetailsContract;
import br.pedroso.movies.movieDetails.usecases.GetMovieDetails;
import br.pedroso.movies.movieDetails.usecases.ListSimilarMovies;
import br.pedroso.movies.shared.data.MoviesRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailsPresenterModule {
    private MovieDetailsContract.View view;

    public MovieDetailsPresenterModule(MovieDetailsContract.View view) {
        this.view = view;
    }

    @Provides
    MovieDetailsContract.View provideMovieDetailsView() {
        return view;
    }

    @Provides
    GetMovieDetails provideGetMovieDetailsUseCase(MoviesRepository moviesRepository) {
        return new GetMovieDetails(moviesRepository);
    }

    @Provides
    ListSimilarMovies provideListSimilarMoviesUseCase(MoviesRepository moviesRepository) {
        return new ListSimilarMovies(moviesRepository);
    }
}
