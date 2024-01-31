package com.korchagin.breaking.domain.repository

import com.korchagin.breaking.domain.common.Result
import com.korchagin.breaking.domain.model.BboyEntity
import kotlinx.coroutines.flow.Flow

interface BboyRepository {
    suspend fun getCurrentBboy(name: String): Flow<Result<BboyEntity>>
    suspend fun getAllBboys(rating: String): Flow<Result<List<BboyEntity>>>
}