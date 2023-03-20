package com.example.examen.ui.movies

import com.example.examen.data.database.entities.MoviesEntity
import com.example.examen.data.model.MoviesModel

sealed class MoviesViewState {
    object Start : MoviesViewState()
    data class Movies(val movies: List<MoviesModel>) : MoviesViewState()
}