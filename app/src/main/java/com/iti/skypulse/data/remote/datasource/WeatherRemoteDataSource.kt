package com.iti.skypulse.data.remote.datasource

import com.iti.skypulse.data.remote.dto.ForecastResponseDto
import com.iti.skypulse.data.remote.dto.GeoPlaceDto
import com.iti.skypulse.data.remote.dto.WeatherResponseDto

interface WeatherRemoteDataSource {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): WeatherResponseDto
    suspend fun getFiveDayForecast(latitude: Double, longitude: Double): ForecastResponseDto

    suspend fun searchPlaces(query: String): List<GeoPlaceDto>

}