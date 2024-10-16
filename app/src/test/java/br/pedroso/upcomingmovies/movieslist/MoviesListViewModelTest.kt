package br.pedroso.upcomingmovies.movieslist

import app.cash.turbine.test
import br.pedroso.upcomingmovies.TestCoroutinesRule
import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.movieslist.MoviesListUiEvent.ClickedOnMovie
import br.pedroso.upcomingmovies.movieslist.MoviesListViewModelEvent.NavigateToMovieDetails
import br.pedroso.upcomingmovies.movieslist.fakes.AlwaysFailingUpcomingMoviesList
import br.pedroso.upcomingmovies.movieslist.fakes.AlwaysSuccessfulUpcomingMoviesList
import com.appmattus.kotlinfixture.kotlinFixture
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MoviesListViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutinesRule()

    private val fixture = kotlinFixture()

    @Test
    fun `Given repository fails, When fetching upcoming movies, Then display error state`() =
        runTest {
            val viewModel = MoviesListViewModel(AlwaysFailingUpcomingMoviesList())

            viewModel.uiState.test {
                assertThat(awaitItem()).isEqualTo(MoviesListUiState.Loading)
                assertThat(awaitItem()).isEqualTo(
                    MoviesListUiState.Error(
                        AlwaysFailingUpcomingMoviesList.defaultException,
                    ),
                )
            }
        }

    @Test
    fun `Given repository succeeds, When fetching upcoming movies, Then display movies`() =
        runTest {
            val movies = fixture<List<Movie>>()

            val viewModel = MoviesListViewModel(AlwaysSuccessfulUpcomingMoviesList(movies))

            viewModel.uiState.test {
                assertThat(awaitItem()).isEqualTo(MoviesListUiState.Loading)
                assertThat(awaitItem()).isEqualTo(MoviesListUiState.DisplayMovies(movies))
            }
        }

    @Test
    fun `Given repository returns an empty list, When fetching upcoming movies, Then display empty state`() =
        runTest {
            val viewModel = MoviesListViewModel(AlwaysSuccessfulUpcomingMoviesList(emptyList()))

            viewModel.uiState.test {
                assertThat(awaitItem()).isEqualTo(MoviesListUiState.Loading)
                assertThat(awaitItem()).isEqualTo(MoviesListUiState.Empty)
            }
        }

    @Test
    fun `Given a movie, When clicking on it, Then navigate to movie details`() = runTest {
        val viewModel = MoviesListViewModel(AlwaysSuccessfulUpcomingMoviesList(emptyList()))

        val clickedMovie = fixture<Movie>()

        viewModel.onUiEvent(ClickedOnMovie(clickedMovie))

        viewModel.viewModelEvent.test {
            assertThat(awaitItem()).isEqualTo(NavigateToMovieDetails(clickedMovie))
        }
    }
}
