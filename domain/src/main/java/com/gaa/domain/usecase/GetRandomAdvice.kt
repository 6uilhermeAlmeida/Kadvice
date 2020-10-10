package com.gaa.domain.usecase

import com.gaa.domain.repository.AdviceRepository
import javax.inject.Inject

class GetRandomAdvice @Inject constructor(private val adviceRepository: AdviceRepository) {
    suspend operator fun invoke() = adviceRepository.fetchRandomAdvice()
}