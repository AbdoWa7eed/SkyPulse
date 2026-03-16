package com.iti.skypulse.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponseDto(
    @SerializedName("cod") val responseCode: String,
    @SerializedName("message") val message: Int,
    @SerializedName("cnt") val itemCount: Int,
    @SerializedName("list") val forecastItems: List<ForecastItemDto>,
    @SerializedName("city") val city: ForecastCityDto
)

data class ForecastItemDto(
    @SerializedName("dt") val dataCalculatedAt: Long,
    @SerializedName("main") val mainMetrics: MainMetricsDto,
    @SerializedName("weather") val weatherConditions: List<WeatherConditionDto>,
    @SerializedName("clouds") val clouds: CloudsDto,
    @SerializedName("wind") val wind: ForecastWindDto,
    @SerializedName("visibility") val visibilityInMeters: Int,
    @SerializedName("pop") val precipitationProbability: Double,
    @SerializedName("sys") val partOfDay: PartOfDayDto,
    @SerializedName("dt_txt") val dateTimeText: String
)

data class ForecastWindDto(
    @SerializedName("speed") val speedInMetersPerSecond: Double,
    @SerializedName("deg") val directionInDegrees: Int,
    @SerializedName("gust") val gustSpeedInMetersPerSecond: Double
)

data class PartOfDayDto(
    @SerializedName("pod") val partOfDay: String
)

data class ForecastCityDto(
    @SerializedName("id") val cityId: Int,
    @SerializedName("name") val cityName: String,
    @SerializedName("coord") val coordinates: CoordinatesDto,
    @SerializedName("country") val countryCode: String,
    @SerializedName("population") val population: Int,
    @SerializedName("timezone") val timezoneOffsetInSeconds: Int,
    @SerializedName("sunrise") val sunriseTimestamp: Long,
    @SerializedName("sunset") val sunsetTimestamp: Long
)
