package com.iti.skypulse.data.repository

import com.iti.skypulse.data.model.ForecastModel
import com.iti.skypulse.data.model.WeatherModel

interface WeatherRepository {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): Result<WeatherModel>
    suspend fun getFiveDayForecast(latitude: Double, longitude: Double): Result<ForecastModel>
}