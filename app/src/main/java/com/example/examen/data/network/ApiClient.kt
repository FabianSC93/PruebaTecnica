package com.example.examen.data.network

import com.example.examen.data.model.TopMoviesModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {
    @GET("top_rated?api_key=3bef72225f8cdb4be3217225b8e30d80")
    suspend fun getTopMovies(): Response<TopMoviesModel>
}