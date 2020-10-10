package com.gaa.extension

import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment

fun Fragment.getColor(@ColorRes id: Int) = requireContext().getColor(id)