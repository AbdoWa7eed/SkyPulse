package com.iti.skypulse.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    val cacheKey: String,
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean = false,
    val countryCode: String,
    val temperature: Double,
    val feelsLikeTemperature: Double,
    val minimumTemperature: Double,
    val maximumTemperature: Double,
    val weatherDescription: String,
    val weatherIconCode: String,
    val windSpeed: Double,
    val windDirectionDegrees: Int,
    val humidityPercentage: Int,
    val visibilityInMeters: Int,
    val atmosphericPressure: Int,
    val lastUpdated: Long = System.currentTimeMillis()
)