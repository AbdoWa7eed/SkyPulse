package com.iti.skypulse.ui.favorites

import com.iti.skypulse.data.model.ForecastModel
import com.iti.skypulse.data.model.WeatherModel
import java.util.UUID

data class FavoriteLocationItem(
    val weather: WeatherModel,
    val forecast: ForecastModel,
    val uid: String = UUID.randomUUID().toString()
)

sealed class FavoriteLocationsState {
    data object Loading : FavoriteLocationsState()
    data object Empty : FavoriteLocationsState()
    data class Success(val items: List<FavoriteLocationItem>) : FavoriteLocationsState()
    data class Error(val message: String) : FavoriteLocationsState()
}

sealed class FavoriteLocationsEvent {
    data class ShowUndoSnackbar(val item: FavoriteLocationItem) : FavoriteLocationsEvent()
}