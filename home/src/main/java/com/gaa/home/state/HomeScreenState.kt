package com.gaa.home.state

import android.graphics.drawable.Drawable
import androidx.annotation.StringRes

sealed class HomeScreenState(
    open val background: Drawable?,
    open val lightColor: Int?,
    open val darkColor: Int?
) {

    data class Loading(
        val isInitial: Boolean = false,
        override val background: Drawable?,
        override val lightColor: Int?,
        override val darkColor: Int?
    ) : HomeScreenState(background, lightColor, darkColor)

    data class Success(
        val text: String,
        override val background: Drawable?,
        override val lightColor: Int?,
        override val darkColor: Int?,
        val authorPlug: String
    ) : HomeScreenState(background, lightColor, darkColor)

    data class Error(
        @StringRes val textResId: Int,
        override val background: Drawable? = null,
        override val lightColor: Int? = null,
        override val darkColor: Int? = null
    ) : HomeScreenState(background, lightColor, darkColor)

}