package com.example.examen.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen.data.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _uiMoviesState = MutableStateFlow<MoviesViewState>(MoviesViewState.Start)
    val uiMoviesState: StateFlow<MoviesViewState> = _uiMoviesState

    fun getMovies() {
        viewModelScope.launch {
            moviesRepository.getMovies().collectLatest { list ->
                _uiMoviesState.value = MoviesViewState.Movies(list)
            }
        }
    }
}