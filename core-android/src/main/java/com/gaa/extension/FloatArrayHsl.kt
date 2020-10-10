package com.gaa.extension

import androidx.core.graphics.ColorUtils

fun FloatArray.withSaturation(targetSaturation: Float) = this.apply {
    this[1] = targetSaturation
}

fun FloatArray.withLightness(targetLightness: Float) = this.apply {
    this[2] = targetLightness
}

fun FloatArray.toColor(): Int = ColorUtils.HSLToColor(this)