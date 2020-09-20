package com.gaa.home.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.gaa.extension.wait
import com.gaa.home.R
import com.gaa.home.state.HomeScreenState.Content
import com.gaa.home.state.HomeScreenState.Loading
import com.gaa.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.joinAll
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
        fab_home.setOnClickListener { viewModel.requestAdvice() }
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
        iv_bg_home.setImageDrawable(state.drawable)
        state.lightColor.run { setMainColor(this) }
        state.darkColor.run { iv_dim_home.setBackgroundColor(this) }
        presentContent()
    }

    private fun CoroutineScope.onLoading(state: Loading) = launch {
        state.color?.run { setMainColor(this) }
        presentLoading()
    }

    private fun setMainColor(color: Int) = color.run {
        val colorStateList = ColorStateList.valueOf(this)
        tv_home.setTextColor(this)
        fab_home.backgroundTintList = colorStateList
        pgbar_home.indeterminateTintList = colorStateList
    }

    private suspend fun presentContent() = coroutineScope {

        listOf(iv_bg_home, tv_home, iv_dim_home).forEach {
            it.alpha = 0f
            it.scaleY = 1f
            it.scaleX = 1f
            it.visibility = View.VISIBLE
        }

        val backgroundAndLoadingAnimations = listOf(
            launch {
                val startScale = 1.3f
                iv_bg_home.scaleX = startScale
                iv_bg_home.scaleY = startScale

                iv_bg_home.animate().scaleX(1.1f).scaleY(1.1f).alpha(1.1f)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .wait(.3f)
            },
            launch { iv_dim_home.animate().alpha(.8f).wait(.3f) },
            launch { pgbar_home.animate().alpha(0f).wait(.3f) }
        )

        backgroundAndLoadingAnimations.joinAll()

        val textAnimation = launch { tv_home.animate().alpha(1f).wait() }

        textAnimation.join()
    }

    private suspend fun presentLoading() = coroutineScope {
        listOf(
            launch { tv_home.animate().alpha(0f).wait(.3f) },
            launch { iv_dim_home.animate().alpha(0f).wait(.3f) }
        ).joinAll()

        launch { iv_bg_home.animate().scaleY(1f).scaleX(1f).alpha(0f).wait(.6f) }.join()
        launch { pgbar_home.animate().alpha(1f).wait() }.join()
    }

    private suspend fun List<Pair<View, Float>>.animateAlpha() = coroutineScope {
        mapNotNull { (view, targetAlpha) ->
            launch {
                view.visibility = View.VISIBLE
                view.animate().alpha(targetAlpha).wait()
                if (targetAlpha <= 0f) view.visibility = View.INVISIBLE
            }
        }.joinAll()
    }
}