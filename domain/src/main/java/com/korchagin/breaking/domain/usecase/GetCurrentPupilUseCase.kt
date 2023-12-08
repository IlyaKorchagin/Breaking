package com.korchagin.breaking.domain.usecase


import com.korchagin.breaking.domain.repository.PupilRepository
import javax.inject.Inject

class GetCurrentPupilUseCase @Inject constructor(
    private val pupilRepository: PupilRepository
) {
    suspend operator fun invoke(email:String) = pupilRepository.getCurrentPupil(email)
}