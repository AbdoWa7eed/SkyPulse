package com.iti.skypulse.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.R
import com.iti.skypulse.core.error.toMessageRes
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.data.local.location.LocationHelper
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.data.model.HourlyForecastModel
import com.iti.skypulse.data.model.LocationProvider
import com.iti.skypulse.data.model.SavedLocation
import com.iti.skypulse.data.model.WeatherModel
import com.iti.skypulse.data.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherRepository: WeatherRepository,
    private val appPreferences: AppPreferences,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _tempUnit = MutableStateFlow(TempUnit.CELSIUS)
    val tempUnit: StateFlow<TempUnit> = _tempUnit

    init {
        viewModelScope.launch {
            appPreferences.tempUnit.collect { _tempUnit.value = it }
        }
        loadWeather()
    }

    private fun loadWeather() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            val location = resolveLocation()
            if (location == null) {
                _uiState.value = HomeUiState.Error(R.string.error_no_location)
                return@launch
            }

            fetchWeather(location.lat, location.lng)
        }
    }

    private suspend fun resolveLocation(): SavedLocation? {
        return getGpsLocation() ?: getSavedLocation()
    }

    private suspend fun getGpsLocation(): SavedLocation? {
        return locationHelper.getLocation()
            .getOrNull()
            ?.let { location ->
                SavedLocation(
                    lat = location.latitude,
                    lng = location.longitude,
                    provider = LocationProvider.GPS
                ).also { appPreferences.saveLocation(it) }
            }
    }

    private suspend fun getSavedLocation(): SavedLocation? {
        return appPreferences.getSavedLocation()
    }

    private suspend fun fetchWeather(latitude: Double, longitude: Double) {
        val address = locationHelper.getAddressFromLocation(latitude, longitude)

        val weatherDeferred = viewModelScope.async {
            weatherRepository.getCurrentWeather(latitude, longitude)
        }
        val forecastDeferred = viewModelScope.async {
            weatherRepository.getFiveDayForecast(latitude, longitude)
        }

        val weatherResult = weatherDeferred.await()
        val forecastResult = forecastDeferred.await()

        if (weatherResult.isFailure) {
            _uiState.value = HomeUiState.Error(weatherResult.exceptionOrNull().toMessageRes())
            return
        }

        val weather = weatherResult.getOrThrow()
        val hourlyForecasts = forecastResult.getOrNull()
            ?.dailyForecasts
            ?.firstOrNull()
            ?.hourlyForecasts
            ?: emptyList()

        _uiState.value = HomeUiState.Success(
            weather = weather,
            hourlyForecasts = hourlyForecasts,
            address = address
        )
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val weather: WeatherModel,
        val hourlyForecasts: List<HourlyForecastModel>,
        val address: String?
    ) : HomeUiState()
    data class Error(@param:StringRes val messageRes: Int) : HomeUiState()
}