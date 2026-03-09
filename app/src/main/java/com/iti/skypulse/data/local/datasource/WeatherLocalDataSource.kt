package com.iti.skypulse.data.local.datasource

import com.iti.skypulse.data.local.room.entity.ForecastEntity
import com.iti.skypulse.data.local.room.entity.WeatherEntity

interface WeatherLocalDataSource {
    suspend fun getWeather(cityName: String): WeatherEntity?
    suspend fun saveWeather(weather: WeatherEntity)
    suspend fun getForecast(cityName: String): ForecastEntity?
    suspend fun saveForecast(forecast: ForecastEntity)

    suspend fun clearAll()
}