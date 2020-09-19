package com.gaa.data.network.photo

import com.gaa.data.network.photo.dto.PhotoOrientation.Landscape
import com.gaa.data.network.photo.dto.PhotoOrientation.Portrait
import com.gaa.data.network.photo.dto.UnsplashPhotoResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import javax.inject.Inject

class UnsplashService @Inject constructor(private val httpClient: HttpClient) {

    suspend fun fetchPhoto(query: String, isPortrait: Boolean): UnsplashPhotoResponse {

        return httpClient.get("https://api.unsplash.com/search/photos") {

            header("Authorization", "Client-ID $UNSPLASH_API_KEY")

            val orientation = if (isPortrait) Portrait else Landscape
            parameter("orientation", orientation.serialName)
            parameter("query", query)

        }
    }
}