package br.pedroso.upcomingmovies.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.pedroso.upcomingmovies.R
import br.pedroso.upcomingmovies.designsystem.theme.UpcomingMoviesTheme

@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    maxRating: Int = 5,
    starColor: Color = MaterialTheme.colorScheme.primary,
    starBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    @DrawableRes ratingVectorResource: Int = R.drawable.ic_star
) {
    val starVector = ImageVector.vectorResource(ratingVectorResource)
    val starVectorPainter = rememberVectorPainter(image = starVector)

    val width = LocalDensity.current.run { starVectorPainter.intrinsicSize.width.toDp() }
    val height = LocalDensity.current.run { starVectorPainter.intrinsicSize.height.toDp() }
    val totalWidth = width * maxRating

    val currentRating = rating.coerceIn(0f, maxRating.toFloat())

    val ratingPercentage = currentRating / maxRating

    Canvas(
        modifier =
        modifier
            .size(totalWidth, height)
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    ) {
        repeat(maxRating) {
            with(starVectorPainter) {
                translate(it * width.toPx(), 0f) {
                    draw(
                        size = starVectorPainter.intrinsicSize,
                        alpha = 1f,
                        colorFilter = ColorFilter.tint(starBackgroundColor),
                    )
                }
            }
        }

        drawRect(
            color = starColor,
            blendMode = BlendMode.SrcIn,
            size = size.copy(width = totalWidth.toPx() * ratingPercentage),
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun RatingBarPreview(@PreviewParameter(RatingProvider::class) rating: Float) {
    UpcomingMoviesTheme {
        RatingBar(rating = rating)
    }
}

private class RatingProvider : PreviewParameterProvider<Float> {
    override val values: Sequence<Float>
        get() = sequenceOf(
            0f,
            1.5f,
            2.25f,
            3.45f,
            4.8f,
            5f
        )
}
