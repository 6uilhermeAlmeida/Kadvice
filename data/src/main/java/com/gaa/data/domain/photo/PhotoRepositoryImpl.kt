package com.gaa.data.domain.photo

import com.gaa.data.network.photo.UnsplashService
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val unsplashService: UnsplashService) :
    PhotoRepository {

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