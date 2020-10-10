package com.gaa.data.repository

import com.gaa.data.api.photo.UnsplashService
import com.gaa.domain.model.Photo
import com.gaa.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val unsplashService: UnsplashService
) : PhotoRepository {

    override suspend fun fetchPhoto(query: String): Photo {
        val networkResponse = unsplashService.fetchPhoto(query)
        val randomPhoto = networkResponse.results.take(10).random()

        return Photo(
            url = randomPhoto.urls.full,
            photographerName = randomPhoto.user.name,
            photographerUserName = randomPhoto.user.userName
        )
    }
}