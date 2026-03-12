package com.iti.skypulse.data.repository

import com.iti.skypulse.data.model.FavoriteWeather
import com.iti.skypulse.data.model.ForecastModel
import com.iti.skypulse.data.model.GeoPlace
import com.iti.skypulse.data.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): Result<WeatherModel>
    suspend fun getFiveDayForecast(latitude: Double, longitude: Double): Result<ForecastModel>

    suspend fun searchPlaces(query: String): Result<List<GeoPlace>>

    fun getFavorites(): Flow<List<FavoriteWeather>>
    suspend fun addFavorite(latitude: Double, longitude: Double) : Result<Unit>
    suspend fun removeFavorite(latitude: Double, longitude: Double)

    suspend fun refreshFavorite(latitude: Double, longitude: Double)


}