package com.gaa.usecase

import com.gaa.data.domain.photo.PhotoRepository
import javax.inject.Inject

class GetPhoto @Inject constructor(private val photoRepository: PhotoRepository) {
    suspend operator fun invoke(query: String) = photoRepository.fetchPhoto(query)
}