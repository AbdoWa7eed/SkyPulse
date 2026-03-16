package com.iti.skypulse.data.repository.weather

import com.iti.skypulse.data.model.weather.FavoriteWeather
import com.iti.skypulse.data.model.weather.ForecastModel
import com.iti.skypulse.data.model.location.GeoPlace
import com.iti.skypulse.data.model.weather.WeatherModel
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