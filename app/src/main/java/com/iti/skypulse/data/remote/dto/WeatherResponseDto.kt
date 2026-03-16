package com.iti.skypulse.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("coord") val coordinates: CoordinatesDto,
    @SerializedName("weather") val weatherConditions: List<WeatherConditionDto>,
    @SerializedName("main") val mainMetrics: MainMetricsDto,
    @SerializedName("visibility") val visibilityInMeters: Int,
    @SerializedName("wind") val wind: WindDto,
    @SerializedName("clouds") val clouds: CloudsDto,
    @SerializedName("dt") val dataCalculatedAt: Long,
    @SerializedName("sys") val systemInfo: SystemInfoDto,
    @SerializedName("timezone") val timezoneOffsetInSeconds: Int,
    @SerializedName("id") val cityId: Int,
    @SerializedName("name") val cityName: String,
    @SerializedName("cod") val responseCode: Int
)


data class WindDto(
    @SerializedName("speed") val speedInMetersPerSecond: Double,
    @SerializedName("deg") val directionInDegrees: Int
)

data class SystemInfoDto(
    @SerializedName("country") val countryCode: String,
    @SerializedName("sunrise") val sunriseTimestamp: Long,
    @SerializedName("sunset") val sunsetTimestamp: Long
)