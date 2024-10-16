package br.pedroso.upcomingmovies.movieslist

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.pedroso.upcomingmovies.designsystem.theme.UpcomingMoviesTheme
import br.pedroso.upcomingmovies.domain.Movie
import coil.compose.AsyncImage

@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    OutlinedCard(modifier = modifier, onClick = onClick) {
        Column {
            AsyncImage(
                modifier = Modifier.aspectRatio(16f / 9),
                placeholder = BrushPainter(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.colorScheme.surface,
                        ),
                    ),
                ),
                model = movie.posterPath,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = spacedBy(4.dp),
            ) {
                Text(text = movie.title, style = MaterialTheme.typography.headlineSmall)

                val releaseDate = movie.releaseDate

                if (!releaseDate.isNullOrBlank()) {
                    Text(text = movie.releaseDate, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    UpcomingMoviesTheme {
        MovieItem(movie = Movie(id = 1, title = "Movie 1", releaseDate = "2024-01-01"))
    }
}
