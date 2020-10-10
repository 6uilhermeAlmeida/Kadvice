package com.gaa.concurrency.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

data class DispatcherProvider(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val cpu: CoroutineDispatcher
)