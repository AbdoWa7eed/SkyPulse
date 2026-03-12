package com.iti.skypulse.ui.favorites.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.iti.skypulse.R
import com.iti.skypulse.ui.favorites.FavoriteLocationItem
import com.iti.skypulse.ui.favorites.FavoriteLocationsEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun rememberFavoriteSnackbarState(
    events: Flow<FavoriteLocationsEvent>,
    onUndo: (FavoriteLocationItem) -> Unit,
): SnackbarHostState {
    val snackbarHostState = remember { SnackbarHostState() }

    val locationRemovedMsg = stringResource(R.string.location_removed)
    val undoLabel = stringResource(R.string.undo)

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is FavoriteLocationsEvent.ShowUndoSnackbar -> {
                    val snackbarData = snackbarHostState.showSnackbar(
                        message = "$locationRemovedMsg ${event.item.weather.cityName}",
                        actionLabel = undoLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (snackbarData == SnackbarResult.ActionPerformed) {
                        onUndo(event.item)
                    }
                }
            }
        }
    }

    return snackbarHostState
}