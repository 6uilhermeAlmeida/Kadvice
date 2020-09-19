package com.gaa.data.domain.photo

interface PhotoRepository {
    suspend fun fetchPhoto(query: String, isPortrait: Boolean): Photo
}