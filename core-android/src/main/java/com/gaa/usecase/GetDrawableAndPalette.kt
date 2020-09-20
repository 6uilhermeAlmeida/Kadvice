package com.gaa.usecase

import android.content.Context
import coil.Coil
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.gaa.extension.getPalette
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetDrawableAndPalette @Inject constructor(
    @ApplicationContext private val context : Context
) {
    suspend operator fun invoke(url: String) = coroutineScope {

        val imageRequest = ImageRequest.Builder(context)
            .allowHardware(false)
            .data(url)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()

        val drawable = Coil.imageLoader(context)
                .execute(imageRequest)
                .drawable

        val palette = drawable?.getPalette()

        return@coroutineScope drawable to palette
    }
}