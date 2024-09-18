package br.pedroso.upcomingmovies.moviedetails

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import br.pedroso.upcomingmovies.R
import br.pedroso.upcomingmovies.designsystem.components.ErrorState
import br.pedroso.upcomingmovies.designsystem.components.LoadingState
import br.pedroso.upcomingmovies.domain.Movie
import br.pedroso.upcomingmovies.domain.MovieDetails
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiEvent.ClickedOnNavigateBack
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiEvent.ClickedOnSimilarMovie
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiState.DisplayMovieDetails
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiState.Error
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiState.Loading
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsViewModelEvent.NavigateBack
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsViewModelEvent.NavigateToMovieDetails

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel,
    modifier: Modifier = Modifier,
    navigateToMovieDetails: (movieId: Int) -> Unit = {},
    navigateBack: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.viewModelEvent.collect { event ->
            when (event) {
                NavigateBack -> navigateBack()
                is NavigateToMovieDetails -> navigateToMovieDetails(event.movie.id)
            }
        }
    }

    MovieDetailsScreen(
        uiState = uiState,
        modifier = modifier,
        onUiEvent = viewModel::onUiEvent,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    uiState: MovieDetailsUiState,
    modifier: Modifier = Modifier,
    onUiEvent: (MovieDetailsUiEvent) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            if (uiState is DisplayMovieDetails) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { onUiEvent(ClickedOnNavigateBack) }) {
                            Image(
                                painterResource(R.drawable.ic_arrow_back),
                                contentDescription = null
                            )
                        }
                    },
                )

            }
        }
    ) {
        Box(
            modifier = Modifier.padding(top = it.calculateTopPadding()),
        ) {
            when (uiState) {
                is DisplayMovieDetails -> MovieDetailsContent(
                    uiState.movieDetails,
                    modifier = Modifier.fillMaxSize(),
                    clickedOnSimilarMovie = { movie ->
                        onUiEvent(ClickedOnSimilarMovie(movie))
                    }
                )

                is Error -> ErrorState(
                    modifier = Modifier.fillMaxSize(),
                    text = stringResource(R.string.failed_to_load_movie_details),
                )

                Loading -> LoadingState(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsScreenPreview(
    @PreviewParameter(MovieDetailsUiStateProvider::class) uiState: MovieDetailsUiState
) {
    MovieDetailsScreen(uiState = uiState)
}

private class MovieDetailsUiStateProvider : PreviewParameterProvider<MovieDetailsUiState> {
    override val values: Sequence<MovieDetailsUiState>
        get() = sequenceOf(
            Loading,
            Error(Throwable()),
            DisplayMovieDetails(
                movieDetails = MovieDetails(
                    movie = Movie(
                        id = 1,
                        title = "Movie 1",
                        releaseDate = "2024-01-01",
                        overview = LoremIpsum(40).values.joinToString(" "),
                    ),
                    similarMovies = (1..10).map { id ->
                        Movie(
                            id = id,
                            title = "Movie ${id + 1}",
                        )
                    }
                )
            )
        )
}
