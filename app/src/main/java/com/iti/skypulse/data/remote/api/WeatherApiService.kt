package com.iti.skypulse.data.remote.api

import com.iti.skypulse.data.remote.dto.ForecastResponseDto
import com.iti.skypulse.data.remote.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): WeatherResponseDto

    @GET("forecast")
    suspend fun getFiveDayForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): ForecastResponseDto
}