package com.gaa.data.domain

import com.gaa.data.domain.advice.AdviceRepository
import com.gaa.data.domain.advice.AdviceRepositoryImpl
import com.gaa.data.domain.photo.PhotoRepository
import com.gaa.data.domain.photo.PhotoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindAdviceRepository(
        adviceRepositoryImpl: AdviceRepositoryImpl
    ): AdviceRepository

    @Binds
    abstract fun bindPhotoRepository(
        photoRepositoryImpl: PhotoRepositoryImpl
    ): PhotoRepository
}