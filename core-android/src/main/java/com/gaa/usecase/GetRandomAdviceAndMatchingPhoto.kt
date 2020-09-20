package com.gaa.usecase

import com.gaa.data.domain.advice.Advice
import com.gaa.data.domain.photo.Photo
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