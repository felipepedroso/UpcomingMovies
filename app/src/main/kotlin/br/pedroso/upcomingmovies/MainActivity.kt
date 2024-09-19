package br.pedroso.upcomingmovies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Surface
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
                Surface {
                    NavHost(
                        modifier = Modifier.fillMaxSize(),
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