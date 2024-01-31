package com.korchagin.breaking.domain.usecase

import com.korchagin.breaking.domain.repository.ElementsRepository
import javax.inject.Inject

class GetBboyUseCase  @Inject constructor (
    private val elementsRepository: ElementsRepository)
{
    suspend operator fun invoke() = elementsRepository.getBboys()
}