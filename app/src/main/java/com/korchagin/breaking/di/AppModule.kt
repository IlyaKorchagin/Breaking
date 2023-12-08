package com.korchagin.breaking.di

import android.app.Application
import android.content.Context
import com.korchagin.breaking.domain.common.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    fun providePreferences(context: Application) = Preferences(context)
}