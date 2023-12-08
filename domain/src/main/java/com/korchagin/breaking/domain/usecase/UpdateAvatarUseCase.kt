package com.korchagin.breaking.domain.usecase

import com.korchagin.breaking.domain.repository.PupilRepository
import java.util.HashMap

class UpdateAvatarUseCase(private val pupilRepository: PupilRepository) {
    suspend operator fun invoke(userId: String, userAvatar: HashMap<String?, Any?>) = pupilRepository.updateAvatar(userId, userAvatar)
}