package com.iti.skypulse.data.remote.datasource

import com.iti.skypulse.data.remote.api.WeatherApiService
import com.iti.skypulse.data.remote.dto.ForecastResponseDto
import com.iti.skypulse.data.remote.dto.WeatherResponseDto

class WeatherRemoteDataSourceImpl
    (private val weatherApiService: WeatherApiService) : WeatherRemoteDataSource {

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): WeatherResponseDto {
        return weatherApiService.getCurrentWeather(latitude, longitude)
    }

    override suspend fun getFiveDayForecast(
        latitude: Double,
        longitude: Double
    ): ForecastResponseDto {
        return weatherApiService.getFiveDayForecast(latitude, longitude)
    }
}