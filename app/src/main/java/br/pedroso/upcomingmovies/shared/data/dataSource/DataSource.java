package br.pedroso.upcomingmovies.shared.data.dataSource;

import java.util.List;

import br.pedroso.upcomingmovies.shared.domain.Movie;
import rx.Observable;

public interface DataSource {
    Observable<List<Movie>> listUpcomingMovies();

    Observable<Movie> getMovieDetails(Integer movieId);

    Observable<List<Movie>> listSimilarMovies(Integer movieId);
}
