package com.korchagin.breaking.domain.usecase

import com.korchagin.breaking.domain.repository.PupilRepository

class GetPupilsUseCase(private val pupilRepository: PupilRepository) {
    suspend operator fun invoke(filter: String) = pupilRepository.getAllPupils(filter)
}