package br.pedroso.movies.movies.usecases;

import br.pedroso.movies.shared.domain.model.Movie;
import br.pedroso.movies.shared.domain.repository.MoviesRepository;
import br.pedroso.movies.shared.domain.usecase.UseCase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListUpcomingMovies extends UseCase<List<Movie>> {

    private MoviesRepository moviesRepository;
    
    @Inject
    public ListUpcomingMovies(MoviesRepository moviesRepository) {
        super(Schedulers.io(), AndroidSchedulers.mainThread());
        this.moviesRepository = moviesRepository;
    }

    @Override
    protected Observable<List<Movie>> buildUseCaseObservable() {
        return moviesRepository.listUpcomingMovies();
    }
}
