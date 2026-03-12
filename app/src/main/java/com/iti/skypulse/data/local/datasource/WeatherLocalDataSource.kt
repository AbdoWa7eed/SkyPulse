package com.iti.skypulse.data.local.datasource

import com.iti.skypulse.data.local.room.entity.ForecastEntity
import com.iti.skypulse.data.local.room.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {
    suspend fun getWeather(cityName: String): WeatherEntity?
    suspend fun saveWeather(weather: WeatherEntity)
    suspend fun getForecast(cityName: String): ForecastEntity?
    suspend fun saveForecast(forecast: ForecastEntity)

    suspend fun clearAll()

    fun getFavorites(): Flow<List<WeatherEntity>>
    suspend fun markAsFavorite(cacheKey: String)
    suspend fun unmarkAsFavorite(cacheKey: String)
}