package com.iti.skypulse.ui.location

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iti.skypulse.ui.components.ErrorSnackbarHost
import com.iti.skypulse.ui.location.components.LocationDialogType
import com.iti.skypulse.ui.location.components.LocationPickerContent
import com.iti.skypulse.ui.location.components.LocationSettingsDialog
import kotlinx.coroutines.launch

@Composable
fun LocationPickerScreen(
    onLocationSet: () -> Unit,
    onMapSelected: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: LocationPickerViewModel = viewModel(factory = LocationPickerViewModelFactory())
    val locationState by viewModel.locationState.collectAsState()
    var dialogType by remember { mutableStateOf<LocationDialogType?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val requestPermission = rememberLocationPermissionHandler(
        onGranted = { viewModel.fetchGpsLocation() },
        onDenied  = { dialogType = LocationDialogType.PERMISSION_DENIED }
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LocationPickerEvent.ShowLocationDisabledDialog ->
                    dialogType = LocationDialogType.LOCATION_DISABLED
                is LocationPickerEvent.RequestLocationPermission ->
                    dialogType = LocationDialogType.PERMISSION_DENIED
                is LocationPickerEvent.ShowSnackBarError ->
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                is LocationPickerEvent.ProceedToHome ->
                    onLocationSet()
            }
        }
    }

    dialogType?.let { type ->
        LocationSettingsDialog(
            type = type,
            onOpenSettings = {
                dialogType = null
                context.startActivity(buildSettingsIntent(context, type))
            },
            onDismiss = { dialogType = null }
        )
    }

    Scaffold(
        snackbarHost = { ErrorSnackbarHost(snackbarHostState) }
    ) { _ ->
        LocationPickerContent(
            locationState = locationState,
            onGpsSelected = { requestPermission() },
            onMapSelected = onMapSelected,
            onConfirm = { viewModel.confirmLocation() },
            onRetry = { viewModel.resetState() }
        )
    }
}