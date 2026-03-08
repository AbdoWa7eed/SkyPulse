package com.iti.skypulse.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.R
import com.iti.skypulse.core.error.toMessageRes
import com.iti.skypulse.data.local.location.LocationHelper
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.data.model.ForecastModel
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


    init {
        loadWeather()
    }

    fun loadWeather() {
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
        val address = getAddress(latitude, longitude)
        val (weatherResult, forecastResult) = fetchWeatherAndForecast(latitude, longitude)

        if (weatherResult.isFailure) {
            _uiState.value = HomeUiState.Error(weatherResult.exceptionOrNull().toMessageRes())
            return
        }

        _uiState.value = HomeUiState.Success(
            weather = weatherResult.getOrThrow(),
            hourlyForecasts = extractTodayHourly(forecastResult),
            address = address
        )
    }

    private suspend fun getAddress(latitude: Double, longitude: Double): String? {
        return locationHelper.getAddressFromLocation(latitude, longitude)
    }

    private suspend fun fetchWeatherAndForecast(
        latitude: Double,
        longitude: Double
    ): Pair<Result<WeatherModel>, Result<ForecastModel>> {
        val weatherDeferred = viewModelScope.async {
            weatherRepository.getCurrentWeather(latitude, longitude)
        }
        val forecastDeferred = viewModelScope.async {
            weatherRepository.getFiveDayForecast(latitude, longitude)
        }
        return Pair(weatherDeferred.await(), forecastDeferred.await())
    }

    private fun extractTodayHourly(forecastResult: Result<ForecastModel>): List<HourlyForecastModel> {
        return forecastResult.getOrNull()
            ?.dailyForecasts
            ?.firstOrNull()
            ?.hourlyForecasts
            ?: emptyList()
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