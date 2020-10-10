package com.gaa.home.viewmodel

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaa.domain.usecase.GetDrawableWithColors
import com.gaa.domain.usecase.GetRandomAdviceAndMatchingPhoto
import com.gaa.extension.networkAvailabilityAsFlow
import com.gaa.extension.toColor
import com.gaa.extension.withLightness
import com.gaa.extension.withSaturation
import com.gaa.home.R
import com.gaa.home.state.HomeScreenState
import com.gaa.home.state.HomeScreenState.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
class HomeViewModel @ViewModelInject constructor(
    private val getRandomAdviceAndMatchingPhoto: GetRandomAdviceAndMatchingPhoto,
    private val getDrawableWithColors: GetDrawableWithColors,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState?>(null)
    val state: StateFlow<HomeScreenState?>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("HomeViewModel", "Error with request.", throwable)
        _state.value = Error(R.string.home_error)
    }

    init {
        requestAdvice()
        viewModelScope.startNetworkListener()
    }

    fun requestAdvice() {
        if (_state.value is Loading) return

        _state.startLoadingState()
        viewModelScope.launch(exceptionHandler) {
            val (advice, photo) = getRandomAdviceAndMatchingPhoto()
            val drawableWithColors = getDrawableWithColors(photo.url)

            val lightColor = drawableWithColors?.lightColorHsl?.withSaturation(1f)?.toColor()
            val darkColor = drawableWithColors?.darkColorHsl?.withLightness(0.2f)?.toColor()
            val drawable = drawableWithColors?.drawable

            _state.value = Success(advice.text, drawable, lightColor, darkColor)
        }
    }

    private fun CoroutineScope.startNetworkListener() = launch {
        connectivityManager.networkAvailabilityAsFlow {
            addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        }.collect { isNetworkAvailable ->
            if (_state.value is Error && isNetworkAvailable) requestAdvice()
        }
    }

    private fun MutableStateFlow<HomeScreenState?>.startLoadingState() {
        value = Loading(value == null, value?.background, value?.lightColor, value?.darkColor)
    }
}

