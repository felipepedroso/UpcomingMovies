package br.pedroso.upcomingmovies.repository

import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MoviesRepository
import br.pedroso.upcomingmovies.network.entities.RetrofitMovieEntity
import br.pedroso.upcomingmovies.network.services.TheMovieDbService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val theMovieDbService: TheMovieDbService
) : MoviesRepository {

    private val retrofitMovieEntityToMovieMapper = RetrofitMovieEntityToMovieMapper()

    override fun listUpcomingMovies(): Single<List<Movie>> {
        return theMovieDbService.listUpcomingMovies()
            .map { response -> response.results.map(retrofitMovieEntityToMovieMapper) }
            .subscribeOn(Schedulers.io())
    }

    override fun getMovieDetails(movieId: Int?): Single<Movie> {
        return theMovieDbService.getMovieDetails(movieId)
            .map(retrofitMovieEntityToMovieMapper)
            .subscribeOn(Schedulers.io())
    }

    override fun listSimilarMovies(movieId: Int?): Single<List<Movie>> {
        return theMovieDbService.listSimilarMovies(movieId)
            .map { response -> response.results.map(retrofitMovieEntityToMovieMapper) }
            .subscribeOn(Schedulers.io())
    }

    private class RetrofitMovieEntityToMovieMapper : (RetrofitMovieEntity) -> Movie {
        override fun invoke(retrofitMovieEntity: RetrofitMovieEntity) = Movie(
            id = retrofitMovieEntity.id,
            title = retrofitMovieEntity.title,
            posterPath = IMAGE_TMDB_BASE_URL + retrofitMovieEntity.posterPath,
            voteAverage = retrofitMovieEntity.voteAverage / MAX_VOTE_AVERAGE,
            releaseDate = retrofitMovieEntity.releaseDate,
            overview = retrofitMovieEntity.overview,
        )

        private companion object {
            const val IMAGE_TMDB_BASE_URL = "https://image.tmdb.org/t/p/w500"

            const val MAX_VOTE_AVERAGE = 10.0
        }
    }
}
