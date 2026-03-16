package com.iti.skypulse.data.remote.api

import com.iti.skypulse.data.remote.dto.ForecastResponseDto
import com.iti.skypulse.data.remote.dto.GeoPlaceDto
import com.iti.skypulse.data.remote.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): WeatherResponseDto

    @GET("data/2.5/forecast")
    suspend fun getFiveDayForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): ForecastResponseDto

    @GET("geo/1.0/direct")
    suspend fun searchPlaces(
        @Query("q") query: String,
        @Query("limit") limit: Int = 5,
    ): List<GeoPlaceDto>
}