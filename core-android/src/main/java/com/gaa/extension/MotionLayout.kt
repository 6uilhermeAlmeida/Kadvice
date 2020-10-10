package com.gaa.extension

import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun MotionLayout.currentStateAsFlow(): StateFlow<Int> {
    val stateFlow = MutableStateFlow(this.currentState)

    val listener = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) = Unit
        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) = Unit
        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) = Unit
        override fun onTransitionCompleted(motionLayout: MotionLayout, p1: Int) {
            stateFlow.value = motionLayout.currentState
        }
    }

    this@currentStateAsFlow.addTransitionListener(listener)
    return stateFlow
}