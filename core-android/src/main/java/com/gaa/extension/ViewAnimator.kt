package com.gaa.extension

import android.view.ViewPropertyAnimator
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun ViewPropertyAnimator.wait(fraction: Float = 1f) = suspendCancellableCoroutine<Unit> {
    check(fraction in (0f..1f))
    setUpdateListener { update ->
        if (update.animatedFraction >= fraction && it.isActive) it.resume(Unit)
    }
    start()
}