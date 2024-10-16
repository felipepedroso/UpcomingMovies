package br.pedroso.upcomingmovies.moviedetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import br.pedroso.upcomingmovies.designsystem.theme.UpcomingMoviesTheme
import br.pedroso.upcomingmovies.designsystem.utils.thenIf
import coil.compose.AsyncImage

@Composable
fun MovieDetailPoster(
    posterUrl: String,
    modifier: Modifier = Modifier,
    clickedOnPoster: (() -> Unit)? = null,
) {
    AsyncImage(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .aspectRatio(2f / 3)
            .thenIf(clickedOnPoster != null) {
                clickable { clickedOnPoster?.invoke() }
            },
        placeholder = BrushPainter(
            Brush.linearGradient(
                listOf(
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.surface,
                ),
            ),
        ),
        model = posterUrl,
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
    )
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailPosterPreview() {
    UpcomingMoviesTheme {
        MovieDetailPoster(posterUrl = "https://path.to.poster")
    }
}
