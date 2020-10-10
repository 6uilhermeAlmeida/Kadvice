package com.gaa.data.repository

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import coil.Coil
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.gaa.concurrency.dispatcher.DispatcherProvider
import com.gaa.domain.model.DrawableWithColors
import com.gaa.domain.repository.DrawableRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DrawableRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatcherProvider: DispatcherProvider
) : DrawableRepository {

    override suspend fun fetchDrawableWithColors(url: String): DrawableWithColors? {
        val imageRequest = ImageRequest.Builder(context)
            .dispatcher(dispatcherProvider.io)
            .allowHardware(false)
            .data(url)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()

        val drawable = Coil.imageLoader(context)
            .execute(imageRequest)
            .drawable ?: return null

        val bitmap = (drawable as BitmapDrawable).bitmap
        val palette = withContext(dispatcherProvider.cpu) { Palette.from(bitmap).generate() }
        val lightColor =  palette.run { lightVibrantSwatch ?: lightMutedSwatch }?.hsl
        val darkColor = palette.run { darkVibrantSwatch ?: darkMutedSwatch }?.hsl

        return DrawableWithColors(drawable, lightColor, darkColor)
    }
}