package com.iti.skypulse.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.R
import com.iti.skypulse.core.error.toMessageRes
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.data.model.ForecastModel
import com.iti.skypulse.data.model.HourlyForecastModel
import com.iti.skypulse.data.model.WeatherModel
import com.iti.skypulse.data.local.location.LocationHelper
import com.iti.skypulse.data.repository.WeatherRepository
import com.iti.skypulse.data.repository.settings.SettingsRepository
import com.iti.skypulse.core.extensions.toStateFlow
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    val tempUnit: StateFlow<TempUnit> = settingsRepository.tempUnit
        .toStateFlow(viewModelScope, TempUnit.CELSIUS)
    val windUnit: StateFlow<WindUnit> = settingsRepository.windUnit
        .toStateFlow(viewModelScope, WindUnit.METERS_PER_SECOND)
    val pressureUnit: StateFlow<PressureUnit> = settingsRepository.pressureUnit
        .toStateFlow(viewModelScope, PressureUnit.HPA)

    private val currentLocation = locationHelper.currentLocation

    init {
        observeWeather()
    }

    private fun observeWeather() {
        viewModelScope.launch {
            currentLocation.collect { loc ->
                    if (loc == null) {
                        _uiState.value = HomeUiState.Error(R.string.error_no_location)
                    } else {
                        fetchWeather(loc.lat, loc.lng)
                    }
                }
        }
    }


    fun loadWeather() {
        viewModelScope.launch {
            val loc = currentLocation.first()
            if (loc != null) fetchWeather(loc.lat, loc.lng)
        }
    }

    private suspend fun fetchWeather(latitude: Double, longitude: Double) {
        _uiState.value = HomeUiState.Loading
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
        val weatherDeferred = viewModelScope.async { weatherRepository.getCurrentWeather(latitude, longitude) }
        val forecastDeferred = viewModelScope.async { weatherRepository.getFiveDayForecast(latitude, longitude) }
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