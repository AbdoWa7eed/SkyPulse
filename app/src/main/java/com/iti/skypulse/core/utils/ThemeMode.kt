package com.iti.skypulse.core.utils

import androidx.annotation.StringRes
import com.iti.skypulse.R

enum class ThemeMode(@param:StringRes val labelRes: Int) {
    LIGHT(R.string.light),
    DARK(R.string.dark),
    SYSTEM(R.string.system)
}