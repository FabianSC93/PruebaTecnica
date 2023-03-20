package com.example.examen.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examen.data.database.dao.MoviesDao
import com.example.examen.data.database.entities.MoviesEntity

@Database(entities = [MoviesEntity::class], version = 1)
abstract class DataBase: RoomDatabase() {

    abstract fun getMoviesDao() : MoviesDao

}