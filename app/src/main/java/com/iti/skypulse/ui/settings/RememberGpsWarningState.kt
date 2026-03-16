package com.iti.skypulse.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.iti.skypulse.data.model.location.LocationProvider
import com.iti.skypulse.data.model.location.SavedLocation
import com.iti.skypulse.ui.common.GpsStateObserver
import kotlinx.coroutines.flow.Flow

@Composable
fun rememberGpsWarningState(
    savedLocation: SavedLocation?,
    isGpsAvailable: () -> Boolean,
    events: Flow<SettingsEvent>
): Boolean {
    var showWarning by remember { mutableStateOf(false) }
    val currentIsGpsAvailable by rememberUpdatedState(isGpsAvailable)

    fun check() {
        if (savedLocation?.provider == LocationProvider.GPS) {
            showWarning = !currentIsGpsAvailable()
        }
    }

    LaunchedEffect(Unit) {
        events.collect { showWarning = true }
    }

    LaunchedEffect(savedLocation) {
        when (savedLocation?.provider) {
            LocationProvider.MAP -> showWarning = false
            LocationProvider.GPS -> showWarning = !currentIsGpsAvailable()
            null                 -> Unit
        }
    }

    GpsStateObserver(onGpsStateChanged = { check() })


    return showWarning
}