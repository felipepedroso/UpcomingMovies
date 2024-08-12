package br.pedroso.upcomingmovies.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pedroso.upcomingmovies.domain.GetMovieDetails
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiEvent.ClickedOnNavigateBack
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiEvent.ClickedOnSimilarMovie
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiState.DisplayMovieDetails
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiState.Error
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsUiState.Loading
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsViewModelEvent.NavigateBack
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsViewModelEvent.NavigateToMovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMovieDetails: GetMovieDetails,
) : ViewModel() {
    private val _uiState = MutableStateFlow<MovieDetailsUiState>(Loading)
    val uiState = _uiState.asStateFlow()

    private val _viewModelEvent = MutableSharedFlow<MovieDetailsViewModelEvent>()
    val viewModelEvent = _viewModelEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _uiState.update { Loading }

            val movieId = savedStateHandle.get<Int>(MovieDetailsActivity.EXTRA_MOVIE_ID)

            if (movieId == null) {
                _uiState.value = Error(InvalidMovieId())
                return@launch
            } else {


                runCatching { getMovieDetails(movieId) }
                    .onSuccess { movieDetails ->
                        _uiState.value = DisplayMovieDetails(movieDetails)
                    }
                    .onFailure { error ->
                        _uiState.value = Error(FailedToFetchMovieDetails(error))
                    }
            }
        }
    }

    fun onUiEvent(event: MovieDetailsUiEvent) {
        when (event) {
            is ClickedOnSimilarMovie -> {
                viewModelScope.launch {
                    _viewModelEvent.emit(NavigateToMovieDetails(event.movie))
                }
            }

            ClickedOnNavigateBack -> viewModelScope.launch {
                _viewModelEvent.emit(NavigateBack)
            }
        }
    }

    class InvalidMovieId : Throwable("Please use a valid movie id to fetch its details.")
    class FailedToFetchMovieDetails(cause: Throwable) :
        Throwable("Failed to fetch the movie details.", cause)
}
