package com.iti.skypulse.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class WarningColors(
    val warning: Color,
    val onWarning: Color
)

val LocalWarningColors = staticCompositionLocalOf {
    WarningColors(warning = Warning, onWarning = Color.White)
}