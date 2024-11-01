package br.pedroso.upcomingmovies.movieslist

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.pedroso.upcomingmovies.R
import br.pedroso.upcomingmovies.domain.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesList(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    clickedOnMovie: (movie: Movie) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(text = stringResource(R.string.app_name)) }) }) {
        LazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = spacedBy(16.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding(),
            ),
        ) {
            items(movies) { movie ->
                MovieItem(
                    modifier = Modifier.fillMaxWidth(),
                    movie = movie,
                    onClick = { clickedOnMovie(movie) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesListPreview() {
    MoviesList(
        movies = (1..10).map {
            Movie(id = it, title = "Movie $it", releaseDate = "2024-01-01")
        },
    )
}
