package com.korchagin.breaking.domain.repository

import com.korchagin.breaking.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun uploadImage(data: ByteArray, email: String): Flow<Resource<String>>
    suspend fun downloadImage(): Flow<Resource<Boolean>>
}