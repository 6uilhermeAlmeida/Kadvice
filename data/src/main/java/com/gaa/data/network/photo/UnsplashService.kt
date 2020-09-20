package com.gaa.data.network.photo

import com.gaa.data.network.photo.dto.UnsplashPhotoResponse
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class UnsplashService @Inject constructor(private val httpClient: HttpClient) {

    suspend fun fetchPhoto(query: String): UnsplashPhotoResponse {
        return httpClient.get("https://api.unsplash.com/search/photos") {
            header("Authorization", "Client-ID $UNSPLASH_API_KEY")
            parameter("query", query)
            parameter("orderBy", "latest")
        }
    }
}