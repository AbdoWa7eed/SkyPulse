package com.iti.skypulse.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoordinatesDto(
    @SerializedName("lon") val longitude: Double,
    @SerializedName("lat") val latitude: Double
)

data class WeatherConditionDto(
    @SerializedName("id") val conditionId: Int,
    @SerializedName("main") val conditionGroup: String,
    @SerializedName("description") val conditionDescription: String,
    @SerializedName("icon") val iconCode: String
)

data class MainMetricsDto(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("feels_like") val feelsLikeTemperature: Double,
    @SerializedName("temp_min") val minimumTemperature: Double,
    @SerializedName("temp_max") val maximumTemperature: Double,
    @SerializedName("pressure") val atmosphericPressure: Int,
    @SerializedName("humidity") val humidityPercentage: Int
)

data class CloudsDto(
    @SerializedName("all") val cloudinessPercentage: Int
)