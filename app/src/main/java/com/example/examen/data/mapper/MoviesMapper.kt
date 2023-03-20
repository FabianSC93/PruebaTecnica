package com.example.examen.data.mapper

import com.example.examen.data.database.entities.MoviesEntity
import com.example.examen.data.model.MoviesModel
import com.example.examen.data.model.TopMoviesModel

object MoviesMapper {

    fun getMoviesModel(
        moviesEntity: List<MoviesEntity>
    ): List<MoviesModel> = moviesEntity.map {
        MoviesModel(
            it.poster,
            it.title,
            it.date,
            it.voteAverage
        )
    } ?: emptyList()

    fun getMoviesEntity(
        topMoviesModel: TopMoviesModel?
    ): List<MoviesEntity> = topMoviesModel?.results?.map {
        MoviesEntity(
            poster = "https://image.tmdb.org/t/p/w500${it.poster_path}",
            title = it.title,
            date = it.release_date,
            voteAverage = it.vote_average
        )
    } ?: emptyList()

}