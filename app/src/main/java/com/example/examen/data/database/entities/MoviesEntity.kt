package com.example.examen.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class MoviesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "poster")
    val poster:String,
    @ColumnInfo(name = "title")
    val title:String,
    @ColumnInfo(name = "date")
    val date:String,
    @ColumnInfo(name = "voteAverage")
    val voteAverage:String
)