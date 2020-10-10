package com.gaa.home.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import coil.load
import com.gaa.extension.currentStateAsFlow
import com.gaa.extension.getColor
import com.gaa.home.R
import com.gaa.home.databinding.HomeFragmentBinding
import com.gaa.home.state.HomeScreenState
import com.gaa.home.state.HomeScreenState.*
import com.gaa.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = HomeFragmentBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.run {
            collectState()
            setLoadingListener()
        }
    }

    private fun LifecycleCoroutineScope.collectState() = launchWhenResumed {
        viewModel.state.filterNotNull().collect { state -> state.apply() }
    }

    private fun LifecycleCoroutineScope.setLoadingListener() = launchWhenResumed {
        binding.homeMotionLayout.currentStateAsFlow().collect { currentState ->
            val isLoading = currentState == R.id.loading
            if (isLoading) viewModel.requestAdvice()
            binding.homeMotionLayout.enableTransition(R.id.startToLoading, !isLoading)
        }
    }

    private fun HomeScreenState.apply() = binding.run {
        val lightColor = lightColor ?: getColor(R.color.lightGrey)
        tvHome.setTextColor(lightColor)
        pgbarHome.indeterminateTintList = ColorStateList.valueOf(lightColor)
        ivDimHome.setBackgroundColor(darkColor ?: getColor(R.color.darkGrey))

        val shouldLoadDrawable: Boolean

        when (this@apply) {
            is Loading -> {
                shouldLoadDrawable = ivBgHome.drawable == null
                startLoadingAnimation(isInitial)
            }

            is Success, is Error -> {
                shouldLoadDrawable = true
                tvHome.text =  when(this@apply) {
                    is Success -> text
                    is Error -> getString(textResId)
                    else -> throw IllegalStateException()
                }
                startContentAnimation()
            }
        }

        if (shouldLoadDrawable) ivBgHome.load(background) { crossfade(1000) }
    }

    private fun startContentAnimation() = binding.homeMotionLayout.run {
        when (currentState) {
            R.id.initial_loading -> setTransition(R.id.initToStart).also { transitionToEnd() }
            R.id.loading -> setTransition(R.id.startToLoading).also { transitionToStart() }
        }
    }

    private fun startLoadingAnimation(isInitialLoading: Boolean) = binding.homeMotionLayout.run {
        if (isInitialLoading) setTransition(R.id.initToStart).also { transitionToStart() }
        else setTransition(R.id.startToLoading).also { transitionToEnd() }
    }
}