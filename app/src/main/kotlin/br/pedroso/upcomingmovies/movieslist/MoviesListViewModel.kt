package br.pedroso.upcomingmovies.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pedroso.upcomingmovies.domain.MoviesRepository
import br.pedroso.upcomingmovies.movieslist.MoviesListUiState.DisplayMovies
import br.pedroso.upcomingmovies.movieslist.MoviesListUiState.Empty
import br.pedroso.upcomingmovies.movieslist.MoviesListUiState.Error
import br.pedroso.upcomingmovies.movieslist.MoviesListUiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<MoviesListUiState>(Loading)
    val uiState: StateFlow<MoviesListUiState> = _uiState.asStateFlow()

    private val _viewModelEvent = MutableSharedFlow<MoviesListViewModelEvent>()
    val viewModelEvent: SharedFlow<MoviesListViewModelEvent> = _viewModelEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _uiState.update { Loading }

            runCatching { moviesRepository.listUpcomingMovies() }
                .onFailure { cause ->
                    _uiState.update { Error(cause) }
                }
                .onSuccess { movies ->
                    _uiState.update {
                        if (movies.isEmpty()) {
                            Empty
                        } else {
                            DisplayMovies(movies = movies)
                        }
                    }
                }
        }
    }

    fun onUiEvent(event: MoviesListUiEvent) {
        when (event) {
            is MoviesListUiEvent.ClickedOnMovie -> viewModelScope.launch {
                _viewModelEvent.emit(MoviesListViewModelEvent.NavigateToMovieDetails(event.movie))
            }
        }
    }
}
