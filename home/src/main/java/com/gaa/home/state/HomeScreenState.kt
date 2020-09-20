package com.gaa.home.state

import android.graphics.drawable.Drawable

sealed class HomeScreenState {

    data class Loading(val color: Int? = null) : HomeScreenState()

    sealed class Content(
        open val text: String,
        open val drawable: Drawable,
        open val lightColor: Int,
        open val darkColor: Int
    ) : HomeScreenState() {

        data class Success(
            override val text: String,
            override val drawable: Drawable,
            override val lightColor: Int,
            override val darkColor: Int
        ) : Content(text, drawable, lightColor, darkColor)

        data class Error(
            override val text: String,
            override val drawable: Drawable,
            override val lightColor: Int,
            override val darkColor: Int
        ) : Content(text, drawable, lightColor, darkColor)
    }
}