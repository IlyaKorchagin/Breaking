package com.korchagin.breaking.domain.usecase

import com.korchagin.breaking.domain.repository.StorageRepository


class UploadImageUseCase(private val storageRepository: StorageRepository) {
    suspend operator fun invoke(data: ByteArray) = storageRepository.uploadImage(data)
}