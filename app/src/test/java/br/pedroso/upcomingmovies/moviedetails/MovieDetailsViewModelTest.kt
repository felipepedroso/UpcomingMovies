package br.pedroso.upcomingmovies.moviedetails

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import br.pedroso.upcomingmovies.TestCoroutinesRule
import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MovieDetails
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiEvent.ClickedOnNavigateBack
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiEvent.ClickedOnSimilarMovie
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiState.DisplayMovieDetails
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiState.Error
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiState.Loading
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsViewModel.FailedToFetchMovieDetails
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsViewModel.InvalidMovieId
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsViewModelEvent.NavigateBack
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsViewModelEvent.NavigateToMovieDetails
import br.pedroso.upcomingmovies.moviedetails.fakes.AlwaysFailingGetMovieDetails
import br.pedroso.upcomingmovies.moviedetails.fakes.AlwaysSuccessfulGetMovieDetails
import com.appmattus.kotlinfixture.kotlinFixture
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MovieDetailsViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutinesRule()

    private val fixture = kotlinFixture()

    @Test
    fun `Given saveStateHandle does not contain movieId, When the screen is created, Then error is displayed`() =
        runTest {
            val viewModel = MovieDetailsViewModel(
                savedStateHandle = SavedStateHandle(),
                moviesRepository = AlwaysSuccessfulGetMovieDetails(fixture()),
            )

            viewModel.uiState.test {
                assertThat(awaitItem()).isEqualTo(Loading)

                val error = awaitItem() as Error

                assertThat(error.cause).isInstanceOf(InvalidMovieId::class.java)
            }
        }

    @Test
    fun `Given getMovieDetails fails, When the screen is created, Then error is displayed`() =
        runTest {
            val viewModel = MovieDetailsViewModel(
                savedStateHandle = SavedStateHandle().apply {
                    this[MovieDetailsActivity.EXTRA_MOVIE_ID] = fixture<Int>()
                },
                moviesRepository = AlwaysFailingGetMovieDetails(),
            )

            viewModel.uiState.test {
                assertThat(awaitItem()).isEqualTo(Loading)

                val error = awaitItem() as Error

                assertThat(error.cause).isInstanceOf(FailedToFetchMovieDetails::class.java)
            }
        }

    @Test
    fun `Given getMovieDetails returns the movie details successfully, When the screen is created, Then movie details are displayed`() =
        runTest {
            val movieDetails = fixture<MovieDetails>()

            val viewModel = MovieDetailsViewModel(
                savedStateHandle = SavedStateHandle().apply {
                    this[MovieDetailsActivity.EXTRA_MOVIE_ID] = fixture<Int>()
                },
                moviesRepository = AlwaysSuccessfulGetMovieDetails(movieDetails),
            )

            viewModel.uiState.test {
                assertThat(awaitItem()).isEqualTo(Loading)

                assertThat(awaitItem()).isEqualTo(DisplayMovieDetails(movieDetails))
            }
        }

    @Test
    fun `When clicking on a similar movie, Then navigate to movie details`() = runTest {
        val similarMovie = fixture<Movie>()

        val viewModel = MovieDetailsViewModel(
            savedStateHandle = SavedStateHandle().apply {
                this[MovieDetailsActivity.EXTRA_MOVIE_ID] = fixture<Int>()
            },
            moviesRepository = AlwaysSuccessfulGetMovieDetails(fixture()),
        )

        viewModel.onUiEvent(ClickedOnSimilarMovie(similarMovie))

        viewModel.viewModelEvent.test {
            assertThat(awaitItem()).isEqualTo(NavigateToMovieDetails(similarMovie))
        }
    }

    @Test
    fun `When clicking on navigate back, Then navigate back`() = runTest {
        val viewModel = MovieDetailsViewModel(
            savedStateHandle = SavedStateHandle().apply {
                this[MovieDetailsActivity.EXTRA_MOVIE_ID] = fixture<Int>()
            },
            moviesRepository = AlwaysSuccessfulGetMovieDetails(fixture()),
        )

        viewModel.onUiEvent(ClickedOnNavigateBack)

        viewModel.viewModelEvent.test {
            assertThat(awaitItem()).isEqualTo(NavigateBack)
        }
    }
}
