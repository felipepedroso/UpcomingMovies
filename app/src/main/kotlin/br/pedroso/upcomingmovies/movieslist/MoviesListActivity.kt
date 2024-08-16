package br.pedroso.upcomingmovies.movieslist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import br.pedroso.upcomingmovies.designsystem.theme.UpcomingMoviesTheme
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListActivity : AppCompatActivity() {
    private val viewModel: MoviesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UpcomingMoviesTheme {
                MoviesListScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = viewModel,
                    navigateToMovieDetails = { movieId ->
                        MovieDetailsActivity.openMovieDetails(context = this, movieId = movieId)
                    }
                )
            }
        }
    }
}
