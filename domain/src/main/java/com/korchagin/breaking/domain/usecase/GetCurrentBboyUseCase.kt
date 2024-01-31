package com.korchagin.breaking.domain.usecase


import com.korchagin.breaking.domain.repository.BboyRepository
import javax.inject.Inject

class GetCurrentBboyUseCase @Inject constructor(
    private val bboyRepository: BboyRepository
) {
    suspend operator fun invoke(name:String) = bboyRepository.getCurrentBboy(name)
}