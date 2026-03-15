package com.iti.skypulse.ui.forecast

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.R
import com.iti.skypulse.core.error.toMessageRes
import com.iti.skypulse.data.model.weather.ForecastModel
import com.iti.skypulse.data.repository.weather.WeatherRepository
import com.iti.skypulse.data.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForecastViewModel(
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ForecastUiState>(ForecastUiState.Loading)
    val uiState: StateFlow<ForecastUiState> = _uiState

    init {
        loadForecast()
    }

    fun loadForecast() {
        viewModelScope.launch {
            _uiState.value = ForecastUiState.Loading
            settingsRepository.savedLocation.collect { location ->
                if (location == null) {
                    _uiState.value = ForecastUiState.Error(R.string.error_no_location)
                } else {
                    fetchForecast(location.lat, location.lng)
                }
            }
        }
    }

    private suspend fun fetchForecast(latitude: Double, longitude: Double) {
        weatherRepository.getFiveDayForecast(latitude, longitude)
            .fold(
                onSuccess = { _uiState.value = ForecastUiState.Success(it) },
                onFailure = { _uiState.value = ForecastUiState.Error(it.toMessageRes()) }
            )
    }
}

sealed class ForecastUiState {
    object Loading : ForecastUiState()
    data class Success(val forecast: ForecastModel) : ForecastUiState()
    data class Error(@param:StringRes val messageRes: Int) : ForecastUiState()
}