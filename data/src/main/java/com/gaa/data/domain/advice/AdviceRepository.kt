package com.gaa.data.domain.advice

interface AdviceRepository {
    suspend fun fetchRandomAdvice(): Advice
}