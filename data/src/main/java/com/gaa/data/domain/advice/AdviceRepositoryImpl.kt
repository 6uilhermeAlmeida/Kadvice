package com.gaa.data.domain.advice

import com.gaa.data.network.advice.AdviceService
import javax.inject.Inject

class AdviceRepositoryImpl @Inject constructor(private val adviceRemoteService: AdviceService) : AdviceRepository {

    override suspend fun fetchRandomAdvice(): Advice {

        adviceRemoteService.fetchRandomAdvice().slip.let {
            return Advice(it.id, it.advice)
        }
    }
}

