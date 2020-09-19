package com.gaa.data.domain.photo

import com.gaa.data.network.photo.UnsplashService
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val unsplashService: UnsplashService) : PhotoRepository {

    override suspend fun fetchPhoto(query: String, isPortrait: Boolean): Photo {
        val networkResponse = unsplashService.fetchPhoto(query, isPortrait)
        val randomPhoto = networkResponse.results.random()

        return Photo(
            url = randomPhoto.urls.regular,
            photographerName = randomPhoto.user.name,
            photographerUserName = randomPhoto.user.userName
        )
    }

}