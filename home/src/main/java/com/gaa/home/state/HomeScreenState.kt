package com.gaa.home.state

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.StringRes

sealed class HomeScreenState(
    open val background: Drawable?,
    open val lightColor: Int?,
    open val darkColor: Int?,
    open val authorPlug: String
) {

    data class Loading(
        val isInitial: Boolean = false,
        override val background: Drawable?,
        override val lightColor: Int?,
        override val darkColor: Int?,
        override val authorPlug: String
    ) : HomeScreenState(background, lightColor, darkColor, authorPlug)

    data class Success(
        val text: String,
        override val background: Drawable?,
        override val lightColor: Int?,
        override val darkColor: Int?,
        override val authorPlug: String
    ) : HomeScreenState(background, lightColor, darkColor, authorPlug)

    data class Error(
        @StringRes val textResId: Int,
        override val background: Drawable? = null,
        override val lightColor: Int? = null,
        override val darkColor: Int? = null,
        override val authorPlug: String = ""
    ) : HomeScreenState(background, lightColor, darkColor, authorPlug)

    fun getAdvice(context: Context): String? = (this as? Success)?.text
        ?: (this as? Error)?.textResId?.let { context.getString(it) }
}