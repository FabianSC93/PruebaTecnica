package com.example.examen.di

import android.content.Context
import android.provider.ContactsContract.Data
import androidx.room.Room
import com.example.examen.data.database.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val MOVIES_DATABASE = "movies_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context, DataBase::class.java, MOVIES_DATABASE).build()

    @Singleton
    @Provides
    fun provideMoviesDao(db: DataBase) = db.getMoviesDao()
}