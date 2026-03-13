package com.iti.skypulse.data.local.datasource

import com.iti.skypulse.data.local.room.entity.ForecastEntity
import com.iti.skypulse.data.local.room.entity.LatLngEntity
import com.iti.skypulse.data.local.room.entity.WeatherEntity
import com.iti.skypulse.data.local.room.entity.WeatherWithForecast
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {
    suspend fun getWeather(cacheKey: String, lang: String): WeatherEntity?
    suspend fun saveWeather(weather: WeatherEntity)
    suspend fun deleteWeather(cacheKey: String)

    suspend fun getForecast(cacheKey: String, lang: String): ForecastEntity?
    suspend fun saveForecast(forecast: ForecastEntity)
    suspend fun deleteForecast(cacheKey: String)

    fun getFavorites(lang: String): Flow<List<WeatherWithForecast>>
    suspend fun markAsFavorite(cacheKey: String)
    suspend fun unmarkAsFavorite(cacheKey: String)
    suspend fun isFavorite(cacheKey: String): Boolean

    suspend fun getAllFavoriteLocations(): List<LatLngEntity>

}