package com.gaa.extension

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@SuppressLint("MissingPermission")
fun ConnectivityManager.networkAvailabilityAsFlow(
    requestSetup: NetworkRequest.Builder.() -> Unit
) = callbackFlow<Boolean> {
    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            offer(true)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            offer(false)
        }
    }

    this@networkAvailabilityAsFlow.registerNetworkCallback(
        NetworkRequest.Builder().apply { requestSetup() }.build(),
        networkCallback
    )

    awaitClose { this@networkAvailabilityAsFlow.unregisterNetworkCallback(networkCallback) }
}