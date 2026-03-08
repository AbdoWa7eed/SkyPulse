package com.iti.skypulse.data.model

data class WeatherModel(
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

    val cityName: String,
    val countryCode: String,
)