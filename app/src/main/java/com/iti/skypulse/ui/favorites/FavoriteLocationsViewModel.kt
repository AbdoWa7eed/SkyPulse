package com.iti.skypulse.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.core.extensions.toStateFlow
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.data.model.LocationProvider
import com.iti.skypulse.data.model.SavedLocation
import com.iti.skypulse.data.model.WeatherModel
import com.iti.skypulse.data.repository.settings.SettingsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class FavoriteLocationsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<FavoriteLocationsState>(FavoriteLocationsState.Loading)
    val state: StateFlow<FavoriteLocationsState> = _state.asStateFlow()

    val tempUnit = settingsRepository.tempUnit
        .toStateFlow(viewModelScope, TempUnit.CELSIUS)

    private val _events = MutableSharedFlow<FavoriteLocationsEvent>()
    val events = _events.asSharedFlow()

    private val localFavorites = simulatedItems.toMutableList()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _state.value = FavoriteLocationsState.Loading
            delay(1500)
            _state.value = if (localFavorites.isEmpty()) FavoriteLocationsState.Empty
            else FavoriteLocationsState.Success(localFavorites.toMutableList())
        }
    }

    fun removeFavoriteItem(item: FavoriteLocationItem) {
        val index = localFavorites.indexOf(item)
        localFavorites.remove(item)
        updateState()
        viewModelScope.launch {
            _events.emit(FavoriteLocationsEvent.ShowUndoSnackbar(item, index))
        }
    }
    fun undo(item: FavoriteLocationItem, index: Int) {
        val safeIndex = index.coerceIn(0, localFavorites.size)
        localFavorites.add(safeIndex, item.copy(uid = UUID.randomUUID().toString()))
        updateState()
    }


    private fun updateState() {
        _state.value = if (localFavorites.isEmpty()) FavoriteLocationsState.Empty
        else FavoriteLocationsState.Success(localFavorites.toMutableList())
    }
}
val simulatedItems = listOf(
    FavoriteLocationItem(
        location = SavedLocation(30.0444, 31.2357, LocationProvider.MAP, "Cairo, Egypt"),
        weather = WeatherModel(
            temperature = 305.0, feelsLikeTemperature = 307.0,
            minimumTemperature = 300.0, maximumTemperature = 308.0,
            weatherDescription = "Clear sky", weatherIconCode = "01d",
            windSpeed = 3.0, windDirectionDegrees = 90,
            humidityPercentage = 30, visibilityInMeters = 10000,
            atmosphericPressure = 1010, cityName = "Cairo", countryCode = "EG"
        )
    ),
    FavoriteLocationItem(
        location = SavedLocation(35.6762, 139.6503, LocationProvider.MAP, "Tokyo, Japan"),
        weather = WeatherModel(
            temperature = 297.0, feelsLikeTemperature = 298.0,
            minimumTemperature = 294.0, maximumTemperature = 299.0,
            weatherDescription = "Clear", weatherIconCode = "01d",
            windSpeed = 2.0, windDirectionDegrees = 45,
            humidityPercentage = 55, visibilityInMeters = 10000,
            atmosphericPressure = 1015, cityName = "Tokyo", countryCode = "JP"
        )
    ),
    FavoriteLocationItem(
        location = SavedLocation(48.8566, 2.3522, LocationProvider.MAP, "Paris, France"),
        weather = WeatherModel(
            temperature = 287.0, feelsLikeTemperature = 285.0,
            minimumTemperature = 283.0, maximumTemperature = 290.0,
            weatherDescription = "Light rain", weatherIconCode = "10d",
            windSpeed = 4.5, windDirectionDegrees = 270,
            humidityPercentage = 80, visibilityInMeters = 7000,
            atmosphericPressure = 1008, cityName = "Paris", countryCode = "FR"
        )
    ),
    FavoriteLocationItem(
        location = SavedLocation(40.7128, -74.0060, LocationProvider.MAP, "New York, USA"),
        weather = WeatherModel(
            temperature = 302.0, feelsLikeTemperature = 305.0,
            minimumTemperature = 298.0, maximumTemperature = 306.0,
            weatherDescription = "Thunderstorm", weatherIconCode = "11d",
            windSpeed = 7.0, windDirectionDegrees = 180,
            humidityPercentage = 70, visibilityInMeters = 5000,
            atmosphericPressure = 1002, cityName = "New York", countryCode = "US"
        )
    )
)