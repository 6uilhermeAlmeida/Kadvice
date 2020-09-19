package com.gaa.data.domain.advice

import com.gaa.data.network.advice.AdviceService
import javax.inject.Inject

class AdviceRepositoryImpl @Inject constructor(private val adviceRemoteService: AdviceService) : AdviceRepository {

    override suspend fun fetchRandomAdvice(): Advice {
        val networkResponseAdvice = adviceRemoteService.fetchRandomAdvice().slip
        return networkResponseAdvice.run {
            Advice(id, advice)
        }
    }
}

