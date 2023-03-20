package com.example.examen.data.network

import com.example.examen.data.model.TopMoviesModel
import javax.inject.Inject

class ApiService @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getMovies(): TopMoviesModel? {
        val response = apiClient.getTopMovies()
        return response.body()
    }
}