package com.gaa.usescases

import com.gaa.data.domain.advice.AdviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRandomAdvice @Inject constructor(private val adviceRepository: AdviceRepository) {
    suspend operator fun invoke() = adviceRepository.fetchRandomAdvice()
}