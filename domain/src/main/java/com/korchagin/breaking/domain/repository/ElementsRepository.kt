package com.korchagin.breaking.domain.repository

import com.korchagin.breaking.domain.common.Result
import com.korchagin.breaking.domain.model.BboyEntity
import com.korchagin.breaking.domain.model.ElementEntity
import kotlinx.coroutines.flow.Flow

interface ElementsRepository {

    suspend fun getBboys(): Flow<Result<List<BboyEntity>>>
    suspend fun getFreezeElements(): Flow<Result<List<ElementEntity>>>
    suspend fun getPowerMoveElements(): Flow<Result<List<ElementEntity>>>
    suspend fun getOfpElements(): Flow<Result<List<ElementEntity>>>
    suspend fun getStretchElements(): Flow<Result<List<ElementEntity>>>
}