package com.gaa.domain.model

import android.graphics.drawable.Drawable

class DrawableWithColors(
    val drawable: Drawable,
    val lightColorHsl : FloatArray?,
    val darkColorHsl : FloatArray?
)