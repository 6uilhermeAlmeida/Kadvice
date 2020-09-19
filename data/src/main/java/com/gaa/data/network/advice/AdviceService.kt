package com.gaa.data.network.advice

import com.gaa.data.network.advice.dto.AdviceResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class AdviceService @Inject constructor(private val httpClient: HttpClient) {

    suspend fun fetchRandomAdvice(): AdviceResponse {
        return httpClient.get("https://api.adviceslip.com/advice")
    }

}