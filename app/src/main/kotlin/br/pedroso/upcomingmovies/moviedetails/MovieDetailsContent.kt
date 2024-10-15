package br.pedroso.upcomingmovies.moviedetails

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import br.pedroso.upcomingmovies.R
import br.pedroso.upcomingmovies.designsystem.components.RatingBar
import br.pedroso.upcomingmovies.designsystem.theme.UpcomingMoviesTheme
import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MovieDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsContent(
    movieDetails: MovieDetails,
    modifier: Modifier = Modifier,
    clickedOnSimilarMovie: (movie: Movie) -> Unit = {},
    scrollState: ScrollState = rememberScrollState()
) {
    val movie = movieDetails.movie
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = spacedBy(8.dp),
    ) {
        MovieDetailsHeader(movie, Modifier.fillMaxWidth())

        val releaseDate = movie.releaseDate.orEmpty()

        if (releaseDate.isNotBlank()) {
            MovieDetailSection(
                title = stringResource(id = R.string.textView_movie_release_date_label),
            ) {
                Text(
                    text = releaseDate,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        MovieDetailSection(
            title = stringResource(id = R.string.textView_movie_overview_label),
        ) {
            Text(
                text = movieDetails.movie.overview.orEmpty(),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if(movieDetails.similarMovies.isNotEmpty()) {
            MovieDetailSection(
                title = stringResource(id = R.string.textView_similar_movies_label),
            ) {
                LazyRow(horizontalArrangement = spacedBy(8.dp)) {
                    items(movieDetails.similarMovies) { movie ->
                        MovieDetailPoster(
                            modifier = Modifier
                                .height(180.dp)
                                .semantics { contentDescription = movie.title }
                                .clickable { clickedOnSimilarMovie(movie) },
                            posterUrl = movie.posterPath.orEmpty()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieDetailsHeader(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(4.dp),
    ) {
        MovieDetailPoster(
            modifier = Modifier.height(256.dp),
            posterUrl = movie.posterPath.orEmpty(),
        )

        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineLarge
        )

        val voteAverage = (movie.voteAverage?.toFloat() ?: 0f) * 5f

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = spacedBy(4.dp)
        ) {
            RatingBar(rating = voteAverage)

            Text(
                text = stringResource(id = R.string.movie_rating_format, voteAverage),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun MovieDetailSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier, verticalArrangement = spacedBy(4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
        )

        content()
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MovieDetailsContentPreview() {
    UpcomingMoviesTheme {
        Surface {
            MovieDetailsContent(
                movieDetails = MovieDetails(
                    movie = Movie(
                        id = 1,
                        title = "Movie 1",
                        releaseDate = "2024-01-01",
                        voteAverage = 3.5,
                        overview = LoremIpsum(40).values.joinToString(" "),
                    ),
                    similarMovies = (1..10).map { id ->
                        Movie(
                            id = id,
                            title = "Movie ${id + 1}",
                            releaseDate = "2024-01-01",
                            overview = LoremIpsum(40).values.joinToString(" "),
                        )
                    }
                ),
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
