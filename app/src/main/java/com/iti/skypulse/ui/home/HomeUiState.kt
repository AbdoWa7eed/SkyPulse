package com.iti.skypulse.ui.home

import androidx.annotation.StringRes
import com.iti.skypulse.data.model.weather.HourlyForecastModel
import com.iti.skypulse.data.model.weather.WeatherModel

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val weather: WeatherModel,
        val hourlyForecasts: List<HourlyForecastModel>,
        val address: String?
    ) : HomeUiState()
    data class Error(@param:StringRes val messageRes: Int) : HomeUiState()
}