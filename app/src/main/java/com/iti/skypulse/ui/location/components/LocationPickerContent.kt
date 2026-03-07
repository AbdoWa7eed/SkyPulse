package com.iti.skypulse.ui.location.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iti.skypulse.ui.location.LocationState

@Composable
fun LocationPickerContent(
    locationState: LocationState,
    onGpsSelected: () -> Unit,
    onMapSelected: () -> Unit,
    onConfirm: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = locationState,
        transitionSpec = { fadeIn() togetherWith  fadeOut() },
        label = "location_state",
        modifier = modifier
    ) { state ->
        when (state) {
            is LocationState.NotSet  -> LocationNotSetContent(onGpsSelected, onMapSelected)
            is LocationState.Loading -> LocationLoadingContent(onCancel = onRetry)
            is LocationState.Set     -> LocationSetContent(state, onConfirm, onRetry)
        }
    }
}