package com.iti.skypulse.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.iti.skypulse.R
import com.iti.skypulse.data.model.SavedLocation
import com.iti.skypulse.ui.components.PrimaryAppBar
import com.iti.skypulse.ui.map.components.MapBottomPanel
import com.iti.skypulse.ui.map.components.MapSearchBar
import com.iti.skypulse.ui.map.components.rememberMapActionsState
import com.iti.skypulse.ui.navigation.MapSource
import kotlinx.coroutines.launch

@Composable
fun MapScreen(
    source: MapSource,
    onBack: () -> Unit,
    onNavigateToMain: () -> Unit,
    viewModel: MapViewModel = viewModel(factory = MapViewModelFactory(source))
) {
    val selectionState by viewModel.selectionState.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    var mapLoaded by remember { mutableStateOf(false) }
    var screenLaunchedState by remember { mutableStateOf(false) }
    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            compassEnabled = true,
            myLocationButtonEnabled = true,
            zoomGesturesEnabled = true,
            scrollGesturesEnabled = true,
            tiltGesturesEnabled = false,
            rotationGesturesEnabled = true
        )
    }

    val snackbarHostState = rememberMapActionsState(
        events = viewModel.events,
        onNavigateToMain = onNavigateToMain,
        onNavigateBack = onBack
    )

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            if (event is MapEvent.MoveCameraTo) {
                scope.launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(event.latLng, event.zoom)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(600)
        screenLaunchedState = true
    }

    val confirmedLocation: SavedLocation? = when (val s = selectionState) {
        is MapSelectionState.AddressResolved -> s.location
        is MapSelectionState.WeatherLoaded -> s.location
        else -> null
    }



    Box(modifier = Modifier.fillMaxSize()) {

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

            if (screenLaunchedState) {
                Box(modifier = Modifier.fillMaxSize()) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        uiSettings =  uiSettings,
                        properties = MapProperties(mapType = MapType.NORMAL),
                        onMapLoaded = { mapLoaded = true },
                        onMapClick = { latLng ->
                            viewModel.onMapClick(
                                latLng.latitude,
                                latLng.longitude
                            )
                        }
                    ) {
                        confirmedLocation?.let { loc ->
                            Marker(
                                state = MarkerState(position = LatLng(loc.lat, loc.lng)),
                                title = loc.address
                            )
                        }
                    }

                    if (!mapLoaded) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    MapSearchBar(
                        query = searchQuery,
                        hint = stringResource(R.string.search_hint),
                        results = searchResults,
                        onQueryChange = { viewModel.onSearchQueryChange(it) },
                        onPlaceSelected = { viewModel.onPlaceSelected(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    )

                    if (mapLoaded) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                        ) {
                            MapBottomPanel(
                                selectionState = selectionState,
                                confirmedLocation = confirmedLocation,
                                onConfirm = { viewModel.onConfirm() }
                            )
                        }
                    }
                }
            }

        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 8.dp, vertical = 16.dp)
        )
    }



}
