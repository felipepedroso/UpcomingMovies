package br.pedroso.movies.movieDetails.usecases;

import br.pedroso.movies.domain.model.Movie;
import br.pedroso.movies.domain.repository.MoviesRepository;
import br.pedroso.movies.domain.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetMovieDetails extends UseCase<Movie> {
    private Integer movieId;
    private MoviesRepository moviesRepository;

    @Inject
    public GetMovieDetails(Integer movieId, MoviesRepository moviesRepository) {
        super(Schedulers.io(), AndroidSchedulers.mainThread());
        this.movieId = movieId;
        this.moviesRepository = moviesRepository;
    }

    @Override
    protected Observable<Movie> buildUseCaseObservable() {
        return moviesRepository.getMovieDetails(movieId);
    }
}
