package br.pedroso.upcomingmovies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.pedroso.upcomingmovies.designsystem.theme.UpcomingMoviesTheme
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsScreen
import br.pedroso.upcomingmovies.movieslist.MoviesListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            UpcomingMoviesTheme {
                Scaffold(contentWindowInsets = WindowInsets.statusBars) {
                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        navController = navController,
                        startDestination = MoviesList,
                    ) {
                        composable<MoviesList> {
                            MoviesListScreen(
                                navigateToMovieDetails = { movieId ->
                                    navController.navigate(MovieDetails(movieId))
                                }
                            )
                        }

                        composable<MovieDetails> {
                            MovieDetailsScreen(
                                navigateToMovieDetails = { movieId ->
                                    navController.navigate(MovieDetails(movieId))
                                },
                                navigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}