package com.example.examen.di

import android.app.Activity
import android.content.Context
import com.example.examen.ui.notifications.PushNotifications
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationsModule {

    @Singleton
    @Provides
    fun provideNotifications(@ApplicationContext context: Context) = PushNotifications(context)

}