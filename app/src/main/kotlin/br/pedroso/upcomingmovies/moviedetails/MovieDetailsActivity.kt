package br.pedroso.upcomingmovies.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import br.pedroso.upcomingmovies.designsystem.theme.UpcomingMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            UpcomingMoviesTheme {
                MovieDetailsScreen(
                    modifier = Modifier.safeContentPadding(),
                    viewModel = viewModel,
                    navigateToMovieDetails = { openMovieDetails(this, it) },
                    navigateBack = { finish() }
                )
            }
        }
    }

    companion object {
        const val EXTRA_MOVIE_ID: String = "movie_id"

        fun openMovieDetails(context: Context, movieId: Int) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            context.startActivity(intent)
        }
    }
}
