package com.gaa.extension

import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun MotionLayout.stateAsFlow(): Flow<Int> = callbackFlow {
    offer(currentState)
    val listener = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) = Unit
        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) = Unit
        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) = Unit
        override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
            offer(currentId)
        }
    }
    addTransitionListener(listener)
    awaitClose { removeTransitionListener(listener) }
}