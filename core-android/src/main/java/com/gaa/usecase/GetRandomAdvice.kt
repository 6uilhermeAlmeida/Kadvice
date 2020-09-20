package com.gaa.usecase

import com.gaa.data.domain.advice.AdviceRepository
import javax.inject.Inject

class GetRandomAdvice @Inject constructor(private val adviceRepository: AdviceRepository) {
    suspend operator fun invoke() = adviceRepository.fetchRandomAdvice()
}