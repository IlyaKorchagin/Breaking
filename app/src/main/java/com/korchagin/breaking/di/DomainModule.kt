package com.korchagin.breaking.di

import android.app.Application
import com.korchagin.breaking.domain.repository.ElementsRepository
import com.korchagin.breaking.domain.repository.PupilRepository
import com.korchagin.breaking.domain.repository.StorageRepository
import com.korchagin.breaking.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetCurrentPupil(pupilRepository: PupilRepository): GetCurrentPupilUseCase{
        return GetCurrentPupilUseCase(pupilRepository = pupilRepository)
    }

    @Provides
    fun provideGetPupils(pupilRepository: PupilRepository): GetPupilsUseCase {
        return GetPupilsUseCase(pupilRepository = pupilRepository)
    }

    @Provides
    fun provideUploadImage(storageRepository: StorageRepository): UploadImageUseCase{
        return UploadImageUseCase(storageRepository = storageRepository)
    }

    @Provides
    fun provideUpdateAvatar(pupilRepository: PupilRepository): UpdateAvatarUseCase {
        return UpdateAvatarUseCase(pupilRepository = pupilRepository)
    }

    @Provides
    fun provideFreezeElements(elementsRepository: ElementsRepository): GetFreezeElementsUseCase {
        return GetFreezeElementsUseCase(elementsRepository = elementsRepository)
    }

}


