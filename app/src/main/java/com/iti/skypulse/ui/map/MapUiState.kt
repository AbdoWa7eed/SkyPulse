package com.iti.skypulse.ui.map

import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.data.model.SavedLocation
import com.iti.skypulse.data.model.WeatherModel



sealed class MapSelectionState {
    data object Idle : MapSelectionState()
    data object ResolvingAddress : MapSelectionState()
    data class AddressResolved(val location: SavedLocation) : MapSelectionState()
    data class WeatherLoaded(
        val location: SavedLocation,
        val weather: WeatherModel,
        val tempUnit: TempUnit
    ) : MapSelectionState()
}

sealed class MapEvent {
    data object NavigateToMain : MapEvent()
    data object NavigateBack : MapEvent()
}