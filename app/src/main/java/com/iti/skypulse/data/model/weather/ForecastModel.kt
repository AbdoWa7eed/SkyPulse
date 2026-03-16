package com.iti.skypulse.data.model.weather

data class ForecastModel(
    val cityName: String,
    val latitude:Double,
    val longitude: Double,
    val countryCode: String,
    val dailyForecasts: List<DailyForecastModel>
)

data class DailyForecastModel(
    val dayName: String,
    val date: String,
    val highTemperature: Double,
    val lowTemperature: Double,
    val weatherDescription: String,
    val weatherIconCode: String,

    val hourlyForecasts: List<HourlyForecastModel>
)

data class HourlyForecastModel(
    val time: String,
    val weatherIconCode: String,
    val temperature: Double
)