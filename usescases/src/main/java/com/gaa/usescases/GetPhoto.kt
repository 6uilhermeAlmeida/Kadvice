package com.gaa.usescases

import com.gaa.data.domain.photo.PhotoRepository
import javax.inject.Inject

class GetPhoto @Inject constructor(private val photoRepository: PhotoRepository) {
    suspend operator fun invoke(query: String, isPortrait: Boolean) = photoRepository.fetchPhoto(query, isPortrait)
}