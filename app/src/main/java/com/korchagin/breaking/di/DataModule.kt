package com.korchagin.breaking.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.korchagin.breaking.domain.repository.AuthRepository
import com.korchagin.breaking.data.repository.AuthRepositoryImpl
import com.korchagin.breaking.data.repository.ElementsRepositoryImpl
import com.korchagin.breaking.data.repository.PupilRepositoryImpl
import com.korchagin.breaking.data.repository.StorageRepositoryImpl
import com.korchagin.breaking.data.storage.UserStorage
import com.korchagin.breaking.data.storage.localStorage.UserStorageImpl
import com.korchagin.breaking.domain.repository.ElementsRepository
import com.korchagin.breaking.domain.repository.PupilRepository
import com.korchagin.breaking.domain.repository.StorageRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providePupilRepository(firebaseDatabase: FirebaseDatabase): PupilRepository{
        return PupilRepositoryImpl(firebaseDatabase = firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideStorageRepositoryImpl(firebaseStorage: FirebaseStorage): StorageRepository {
        return StorageRepositoryImpl(firebaseStorage)
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase() = Firebase.database

    @Provides
    @Singleton
    fun provideElementsRepository(firebaseDatabase: FirebaseDatabase): ElementsRepository {
        return ElementsRepositoryImpl(firebaseDatabase = firebaseDatabase)
    }
}