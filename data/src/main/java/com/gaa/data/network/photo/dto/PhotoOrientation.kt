package com.gaa.data.network.photo.dto

sealed class PhotoOrientation(val serialName: String) {
    object Portrait : PhotoOrientation("portrait")
    object Landscape : PhotoOrientation("landscape")
}