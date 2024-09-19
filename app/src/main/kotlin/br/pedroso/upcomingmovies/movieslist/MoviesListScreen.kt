package br.pedroso.upcomingmovies.movieslist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import br.pedroso.upcomingmovies.R
import br.pedroso.upcomingmovies.designsystem.components.ErrorState
import br.pedroso.upcomingmovies.designsystem.components.LoadingState
import br.pedroso.upcomingmovies.movieslist.MoviesListUiEvent.ClickedOnMovie
import br.pedroso.upcomingmovies.movieslist.MoviesListUiState.DisplayMovies
import br.pedroso.upcomingmovies.movieslist.MoviesListUiState.Empty
import br.pedroso.upcomingmovies.movieslist.MoviesListUiState.Error
import br.pedroso.upcomingmovies.movieslist.MoviesListUiState.Loading
import br.pedroso.upcomingmovies.movieslist.MoviesListViewModelEvent.NavigateToMovieDetails

@Composable
fun MoviesListScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesListViewModel = hiltViewModel(),
    navigateToMovieDetails: (movieId: Int) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.viewModelEvent.collect { event ->
            when (event) {
                is NavigateToMovieDetails -> navigateToMovieDetails(event.movie.id)
            }
        }
    }

    MoviesListScreen(
        uiState = uiState,
        onUiEvent = viewModel::onUiEvent,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListScreen(
    uiState: MoviesListUiState,
    onUiEvent: (MoviesListUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.app_name),
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    ) {
        Box(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            when (uiState) {
                is DisplayMovies -> MoviesList(
                    modifier = Modifier.fillMaxSize(),
                    movies = uiState.movies,
                    clickedOnMovie = { movie -> onUiEvent(ClickedOnMovie(movie)) }
                )

                Empty -> ErrorState(
                    modifier = Modifier.fillMaxSize(),
                    text = stringResource(id = R.string.movies_not_available),
                )

                is Error -> ErrorState(
                    modifier = Modifier.fillMaxSize(),
                    text = stringResource(id = R.string.failed_to_load_movies),
                )

                Loading -> LoadingState(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoviesListScreenPreview() {
    MoviesListScreen(uiState = Empty, onUiEvent = {})
}