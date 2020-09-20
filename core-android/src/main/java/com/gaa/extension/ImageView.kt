package com.gaa.extension

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.palette.graphics.Palette
import coil.load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun ImageView.loadImage(url: String) = suspendCancellableCoroutine<Unit> { continuation ->
    load(url) {
        allowHardware(false)
        listener(
            onSuccess = { _, _ -> continuation.resume(Unit) },
            onError = { _, throwable -> continuation.resumeWithException(throwable) }
        )
    }
}

suspend fun Drawable.getPalette(): Palette {
    val bitmap = (this as BitmapDrawable).bitmap

    return withContext(Dispatchers.Default) {
        Palette.from(bitmap).generate()
    }
}