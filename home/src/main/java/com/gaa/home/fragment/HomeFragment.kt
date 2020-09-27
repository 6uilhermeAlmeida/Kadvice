package com.gaa.home.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import coil.load
import com.gaa.home.R
import com.gaa.home.state.HomeScreenState.Content
import com.gaa.home.state.HomeScreenState.Loading
import com.gaa.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.home_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.collectState()

        home_motion_layout.setTransitionListener(object : MotionLayout.TransitionListener {

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

            override fun onTransitionCompleted(mLayout: MotionLayout?, id: Int) {
                if (id == R.id.loading) {
                    viewModel.requestAdvice()
                    mLayout?.enableTransition(R.id.startToLoading, false)
                } else {
                    mLayout?.enableTransition(R.id.startToLoading, true)
                }
            }
        })
    }

    private fun LifecycleCoroutineScope.collectState() = launchWhenResumed {
        viewModel.state.collect {
            when (it) {
                is Loading -> onLoading(it)
                is Content -> onNewContent(it)
            }
        }
    }

    private fun CoroutineScope.onNewContent(state: Content) = launch {
        tv_home.text = state.text
        iv_bg_home.load(state.drawable) {
            crossfade(1000)
        }
        state.lightColor.run { setMainColor(this) }
        state.darkColor.run { iv_dim_home.setBackgroundColor(this) }
        home_motion_layout.setTransition(R.id.startToLoading)
        home_motion_layout.transitionToStart()
    }

    private fun CoroutineScope.onLoading(state: Loading) = launch {
        state.color?.run { setMainColor(this) }
        home_motion_layout.setTransition(R.id.startToLoading)
        home_motion_layout.transitionToEnd()
    }

    private fun setMainColor(color: Int) = color.run {
        val colorStateList = ColorStateList.valueOf(this)
        tv_home.setTextColor(this)
        pgbar_home.indeterminateTintList = colorStateList
    }
}