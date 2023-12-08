package com.korchagin.breaking.data.storage

import androidx.lifecycle.MutableLiveData
import com.korchagin.breaking.data.storage.models.PupilEntry
import com.korchagin.breaking.data.storage.models.User
import com.korchagin.breaking.domain.model.PupilEntity
import com.korchagin.breaking.domain.model.PupilState

interface UserStorage {
    suspend fun getCurrentPupil(curPupil: MutableLiveData<PupilState>): PupilEntity
    fun getAllPupils():List<User>
}