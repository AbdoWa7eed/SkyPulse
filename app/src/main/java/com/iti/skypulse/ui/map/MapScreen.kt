package com.iti.skypulse.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.rememberCameraPositionState
import com.iti.skypulse.ui.components.PrimaryAppBar
import com.iti.skypulse.ui.map.components.MapBottomPanel
import com.iti.skypulse.ui.map.components.MapContent
import com.iti.skypulse.ui.navigation.MapSource

@Composable
fun MapScreen(
    source: MapSource,
    onBack: () -> Unit,
    onNavigateToMain: () -> Unit,
    viewModel: MapViewModel = viewModel(factory = MapViewModelFactory(source))
) {
    val uiState by viewModel.uiState.collectAsState()
    var query by remember { mutableStateOf("") }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            viewModel.initialPosition,
            viewModel.initialZoom)
    }

    val confirmedLocation = when (val s = uiState) {
        is MapSelectionState.AddressResolved -> s.location
        is MapSelectionState.WeatherLoaded   -> s.location
        else                                 -> null
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is MapEvent.NavigateToMain -> onNavigateToMain()
                is MapEvent.NavigateBack   -> onBack()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 20.dp)
    ) {
        PrimaryAppBar(
            title = stringResource(source.titleRes),
            paddingValues = PaddingValues(horizontal = 16.dp),
            onBack = onBack
        )

        MapContent(
            query = query,
            selectionState = uiState,
            confirmedLocation = confirmedLocation,
            cameraPositionState = cameraPositionState,
            onQueryChange = { query = it },
            onMapClick = { lat, lng -> viewModel.onMapClick(lat, lng) },
            bottomPanel = {
                MapBottomPanel(
                    selectionState = uiState,
                    confirmedLocation = confirmedLocation,
                    onConfirm = { viewModel.onConfirm() }
                )
            }
        )
    }
}