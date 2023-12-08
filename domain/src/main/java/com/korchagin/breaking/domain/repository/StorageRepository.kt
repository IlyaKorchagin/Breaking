package com.korchagin.breaking.domain.repository

import com.korchagin.breaking.common.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Url

interface StorageRepository {
    suspend fun uploadImage(data: ByteArray): Flow<Resource<String>>
    suspend fun downloadImage(): Flow<Resource<Boolean>>
}