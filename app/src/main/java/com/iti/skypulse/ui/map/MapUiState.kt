package com.iti.skypulse.ui.map

import com.google.android.gms.maps.model.LatLng
import com.iti.skypulse.core.error.AppException
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.data.model.SavedLocation
import com.iti.skypulse.data.model.WeatherModel



data class MapLocationData(
    val location: SavedLocation,
    val weather: WeatherModel,
    val tempUnit: TempUnit
)

sealed class MapSelectionState {
    data object Idle : MapSelectionState()
    data object ResolvingAddress : MapSelectionState()
    data class AddressResolved(val location: SavedLocation) : MapSelectionState()
    data class WeatherLoaded(val data: MapLocationData) : MapSelectionState()
    data class Confirming(val data: MapLocationData) : MapSelectionState()
}

sealed class MapEvent {
    data object NavigateToMain : MapEvent()
    data object NavigateBack : MapEvent()
    data class ShowError(val error: Throwable) : MapEvent()
    data class MoveCameraTo(val latLng: LatLng, val zoom: Float = 12f) : MapEvent()
}