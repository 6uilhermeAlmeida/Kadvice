package com.gaa.domain.usecase

import com.gaa.domain.repository.DrawableRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetDrawableWithColors @Inject constructor(private val drawableRepository: DrawableRepository) {
    suspend operator fun invoke(url: String) = coroutineScope {
        drawableRepository.fetchDrawableWithColors(url)
    }
}