package com.korchagin.breaking.domain.repository

import com.korchagin.breaking.domain.common.Result
import com.korchagin.breaking.domain.model.PupilEntity
import kotlinx.coroutines.flow.Flow
import java.util.HashMap

interface PupilRepository {
    suspend fun getCurrentPupil(email: String): Flow<Result<PupilEntity>>
    suspend fun getAllPupils(filter: String): Flow<Result<List<PupilEntity>>>
    suspend fun updateAvatar(userId: String, hashMap: HashMap<String?, Any?>): Flow<Result<Boolean>>
    }