package com.gaa.data.repository

import com.gaa.data.api.advice.AdviceService
import com.gaa.domain.model.Advice
import com.gaa.domain.repository.AdviceRepository
import javax.inject.Inject

class AdviceRepositoryImpl @Inject constructor(private val adviceRemoteService: AdviceService) :
    AdviceRepository {

    override suspend fun fetchRandomAdvice(): Advice {
        val networkResponseAdvice = adviceRemoteService.fetchRandomAdvice().slip
        return networkResponseAdvice.run {
            Advice(id, advice)
        }
    }
}

