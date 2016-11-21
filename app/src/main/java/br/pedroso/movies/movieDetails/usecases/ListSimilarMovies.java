package br.pedroso.movies.movieDetails.usecases;

import br.pedroso.movies.domain.model.Movie;
import br.pedroso.movies.domain.repository.MoviesRepository;
import br.pedroso.movies.domain.usecase.UseCase;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListSimilarMovies extends UseCase<List<Movie>> {
    private final Integer movieId;
    private final MoviesRepository moviesRepository;

    public ListSimilarMovies(Integer movieId, MoviesRepository moviesRepository) {
        super(Schedulers.io(), AndroidSchedulers.mainThread());
        this.movieId = movieId;
        this.moviesRepository = moviesRepository;
    }

    @Override
    protected Observable<List<Movie>> buildUseCaseObservable() {
        return moviesRepository.listSimilarMovies(movieId);
    }
}
