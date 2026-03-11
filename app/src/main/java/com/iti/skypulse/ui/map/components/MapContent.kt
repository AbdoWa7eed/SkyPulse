package com.iti.skypulse.ui.map.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.iti.skypulse.R
import com.iti.skypulse.data.model.GeoPlace
import com.iti.skypulse.data.model.SavedLocation

@Composable
fun MapContent(
    query: String,
    searchResults: List<GeoPlace>,
    confirmedLocation: SavedLocation?,
    cameraPositionState: CameraPositionState,
    onQueryChange: (String) -> Unit,
    onPlaceSelected: (GeoPlace) -> Unit,
    onMapClick: (lat: Double, lng: Double) -> Unit,
    bottomPanel: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false,
                mapToolbarEnabled = false,
                compassEnabled = false
            ),
            properties = MapProperties(mapType = MapType.NORMAL),
            onMapClick = { latLng -> onMapClick(latLng.latitude, latLng.longitude) }
        ) {
            confirmedLocation?.let { loc ->
                Marker(
                    state = MarkerState(position = LatLng(loc.lat, loc.lng)),
                    title = loc.address
                )
            }
        }

        MapSearchBar(
            query = query,
            hint = stringResource(R.string.search_hint),
            results = searchResults,
            onQueryChange = onQueryChange,
            onPlaceSelected = onPlaceSelected,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            bottomPanel()
        }
    }
}