package com.gaa.data.api.advice

import com.gaa.data.api.advice.dto.AdviceResponse
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class AdviceService @Inject constructor(private val httpClient: HttpClient) {

    suspend fun fetchRandomAdvice(): AdviceResponse {
        return httpClient.get("https://api.adviceslip.com/advice")
    }

}