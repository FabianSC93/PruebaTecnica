package com.example.examen.data.repositories

import com.example.examen.data.database.dao.MoviesDao
import com.example.examen.data.mapper.MoviesMapper
import com.example.examen.data.network.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: ApiService, private val moviesDao: MoviesDao) {

    suspend fun getMovies() = flow {
        val listMovies = api.getMovies()
        moviesDao.insertMovies(
            MoviesMapper.getMoviesEntity(listMovies)
        )
        emit(MoviesMapper.getMoviesModel(moviesDao.getMovies()))
    }

}