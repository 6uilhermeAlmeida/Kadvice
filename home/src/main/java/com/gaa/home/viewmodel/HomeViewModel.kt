package com.gaa.home.viewmodel

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.core.graphics.ColorUtils
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaa.dispatcher.DispatcherProvider
import com.gaa.home.R
import com.gaa.home.state.HomeScreenState
import com.gaa.home.state.HomeScreenState.Content
import com.gaa.home.state.HomeScreenState.Content.Error
import com.gaa.home.state.HomeScreenState.Content.Success
import com.gaa.home.state.HomeScreenState.Loading
import com.gaa.usecase.GetDrawableAndPalette
import com.gaa.usecase.GetRandomAdviceAndMatchingPhoto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel @ViewModelInject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val getRandomAdviceAndMatchingPhoto: GetRandomAdviceAndMatchingPhoto,
    private val getDrawableAndPalette: GetDrawableAndPalette,
    private val connectivityManager: ConnectivityManager,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState>(Loading())
    val state: StateFlow<HomeScreenState>
        get() = _state

    private var requestAdviceJob: Job? = null

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            if (_state.value is Error) requestAdvice()
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("HomeViewModel", "Error with request.", throwable)
        val darkColor = applicationContext.getColor(R.color.darkGrey)
        _state.value = Error(
            "Error. Please try again later.",
            ColorDrawable(darkColor),
            applicationContext.getColor(R.color.red),
            darkColor
        )
    }

    init {
        requestAdvice()

        val defaultNetworkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        connectivityManager.registerNetworkCallback(defaultNetworkRequest, networkCallback)
    }

    fun requestAdvice() {
        _state.value = Loading((_state.value as? Content)?.lightColor)

        requestAdviceJob?.cancel()
        requestAdviceJob = viewModelScope.launch(exceptionHandler) {

            val (advice, photo) = getRandomAdviceAndMatchingPhoto()

            val (optionalDrawable, palette) = withContext(dispatchers.io) {
                getDrawableAndPalette(photo.url)
            }

            val fallbackLightColor = applicationContext.getColor(R.color.lightGrey)
            val lightColor = palette?.run { lightVibrantSwatch ?: lightMutedSwatch }?.hsl
                ?.withSaturation(1f)
                ?.toColor()
                ?: fallbackLightColor

            val fallbackDarkColor = applicationContext.getColor(R.color.darkGrey)
            val darkColor = palette?.run { darkVibrantSwatch ?: darkMutedSwatch }?.hsl
                ?.withLightness(0.2f)
                ?.toColor()
                ?: fallbackDarkColor

            val drawable = optionalDrawable ?: ColorDrawable(fallbackDarkColor)

            _state.value = Success(advice.text, drawable, lightColor, darkColor)
        }
    }

    private fun FloatArray.withSaturation(targetSaturation: Float) = this.apply {
        this[1] = targetSaturation
    }

    private fun FloatArray.withLightness(targetLightness: Float) = this.apply {
        this[2] = targetLightness
    }

    private fun FloatArray.toColor(): Int = ColorUtils.HSLToColor(this)

    override fun onCleared() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        super.onCleared()
    }
}

