package com.gaa.domain.usecase

import com.gaa.domain.model.Advice
import com.gaa.domain.model.Photo
import com.gaa.domain.repository.AdviceRepository
import com.gaa.domain.repository.PhotoRepository
import javax.inject.Inject

class GetRandomAdviceAndMatchingPhoto @Inject constructor(
    private val adviceRepository: AdviceRepository,
    private val photoRepository: PhotoRepository
) {
    suspend operator fun invoke(): Pair<Advice, Photo> {
        val randomAdvice = adviceRepository.fetchRandomAdvice()
        val matchingPhoto = photoRepository.fetchRandomPhoto(randomAdvice.text)

        return randomAdvice to matchingPhoto
    }
}