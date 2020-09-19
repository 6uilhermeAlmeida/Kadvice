package com.gaa.home.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.gaa.usescases.GetRandomAdviceAndMatchingPhoto

class HomeViewModel @ViewModelInject constructor(
    val getRandomAdviceAndMatchingPhoto: GetRandomAdviceAndMatchingPhoto,
    @Assisted val savedStateHandle: SavedStateHandle
) : ViewModel()