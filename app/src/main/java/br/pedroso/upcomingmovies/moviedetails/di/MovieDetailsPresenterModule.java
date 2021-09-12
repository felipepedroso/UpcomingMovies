package br.pedroso.upcomingmovies.moviedetails.di;

import br.pedroso.upcomingmovies.moviedetails.MovieDetailsContract;
import br.pedroso.upcomingmovies.moviedetails.usecases.GetMovieDetails;
import br.pedroso.upcomingmovies.moviedetails.usecases.ListSimilarMovies;
import br.pedroso.upcomingmovies.domain.MoviesRepository;
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