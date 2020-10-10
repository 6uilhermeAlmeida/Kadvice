package com.gaa.data.di

import com.gaa.data.repository.AdviceRepositoryImpl
import com.gaa.data.repository.DrawableRepositoryImpl
import com.gaa.data.repository.PhotoRepositoryImpl
import com.gaa.domain.repository.AdviceRepository
import com.gaa.domain.repository.DrawableRepository
import com.gaa.domain.repository.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {
    @Binds
    fun bindAdviceRepository(adviceRepositoryImpl: AdviceRepositoryImpl): AdviceRepository

    @Binds
    fun bindPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository

    @Binds
    fun bindDrawableRepository(drawableRepositoryImpl: DrawableRepositoryImpl): DrawableRepository
}