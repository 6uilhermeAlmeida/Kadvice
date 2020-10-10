package com.gaa.domain.repository

import com.gaa.domain.model.Photo

interface PhotoRepository {
    suspend fun fetchRandomPhoto(query: String): Photo
}