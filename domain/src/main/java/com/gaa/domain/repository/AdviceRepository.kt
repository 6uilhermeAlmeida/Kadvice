package com.gaa.domain.repository

import com.gaa.domain.model.Advice

interface AdviceRepository {
    suspend fun fetchRandomAdvice(): Advice
}