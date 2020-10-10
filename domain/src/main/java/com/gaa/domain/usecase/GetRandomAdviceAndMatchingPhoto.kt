package com.gaa.domain.usecase

import com.gaa.domain.model.Advice
import com.gaa.domain.model.Photo
import javax.inject.Inject

class GetRandomAdviceAndMatchingPhoto @Inject constructor(
    private val getRandomAdvice: GetRandomAdvice,
    private val getPhoto: GetPhoto
) {
    suspend operator fun invoke(): Pair<Advice, Photo> {
        val randomAdvice = getRandomAdvice()
        val matchingPhoto = getPhoto(randomAdvice.text)

        return randomAdvice to matchingPhoto
    }
}