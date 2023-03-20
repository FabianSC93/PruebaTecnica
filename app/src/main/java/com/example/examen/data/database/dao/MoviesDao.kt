package com.example.examen.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examen.data.database.entities.MoviesEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies_table")
    suspend fun getMovies():List<MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MoviesEntity>)
}