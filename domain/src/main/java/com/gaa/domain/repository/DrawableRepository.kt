package com.gaa.domain.repository

import com.gaa.domain.model.DrawableWithColors

interface DrawableRepository {
    suspend fun fetchDrawableWithColors(url : String): DrawableWithColors?
}