package com.gaa.usescases

import com.gaa.data.domain.advice.Advice
import com.gaa.data.domain.photo.Photo
import javax.inject.Inject

class GetRandomAdviceAndMatchingPhoto @Inject constructor(
    private val getRandomAdvice: GetRandomAdvice,
    private val getPhoto: GetPhoto
) {
    suspend operator fun invoke(isPortrait: Boolean): Pair<Advice, Photo> {
        val randomAdvice = getRandomAdvice()
        val matchingPhoto = getPhoto(randomAdvice.text, isPortrait)

        return randomAdvice to matchingPhoto
    }
}