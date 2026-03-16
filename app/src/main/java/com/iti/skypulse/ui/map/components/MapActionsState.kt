package com.iti.skypulse.ui.map.components

import android.annotation.SuppressLint
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.iti.skypulse.core.error.toMessageRes
import com.iti.skypulse.ui.map.MapEvent
import kotlinx.coroutines.flow.Flow

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun rememberMapActionsState(
    events: Flow<MapEvent>,
    onNavigateToMain: () -> Unit,
    onNavigateBack: () -> Unit,
): SnackbarHostState {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is MapEvent.NavigateToMain -> onNavigateToMain()
                is MapEvent.NavigateBack -> onNavigateBack()
                is MapEvent.ShowError -> {
                    snackbarHostState.showSnackbar(context.getString(event.error.toMessageRes()))
                }
                else -> Unit
            }
        }
    }

    return snackbarHostState
}