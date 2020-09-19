package com.gaa.data.network.photo.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashPhotoResponse(val results: List<UnsplashPhoto>)

@Serializable
data class UnsplashPhoto(val urls: Urls, val user: UnsplashUser)

@Serializable
data class Urls(
    val raw: String,
    val full: String,
    val regular: String
)

@Serializable
data class UnsplashUser(
    val name: String,
    @SerialName("username") val userName: String
)