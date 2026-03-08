package com.iti.skypulse.ui.preferences

import androidx.compose.runtime.compositionLocalOf

val LocalPreferencesViewModel = compositionLocalOf<PreferencesViewModel> {
    error("No PreferencesViewModel provided")
}