package com.gaa.domain.usecase

import com.gaa.domain.repository.PhotoRepository
import javax.inject.Inject

class GetPhoto @Inject constructor(private val photoRepository: PhotoRepository) {
    suspend operator fun invoke(query: String) = photoRepository.fetchPhoto(query)
}