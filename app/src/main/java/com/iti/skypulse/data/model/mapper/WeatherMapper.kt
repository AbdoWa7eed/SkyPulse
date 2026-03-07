package com.iti.skypulse.data.model.mapper

import com.iti.skypulse.data.local.room.entity.ForecastEntity
import com.iti.skypulse.data.local.room.entity.WeatherEntity
import com.iti.skypulse.data.model.DailyForecastModel
import com.iti.skypulse.data.model.ForecastModel
import com.iti.skypulse.data.model.HourlyForecastModel
import com.iti.skypulse.data.model.WeatherModel
import com.iti.skypulse.data.remote.dto.ForecastItemDto
import com.iti.skypulse.data.remote.dto.ForecastResponseDto
import com.iti.skypulse.data.remote.dto.WeatherResponseDto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun WeatherResponseDto.toWeatherModel() = WeatherModel(
    temperature = mainMetrics.temperature,
    feelsLikeTemperature = mainMetrics.feelsLikeTemperature,
    minimumTemperature = mainMetrics.minimumTemperature,
    maximumTemperature = mainMetrics.maximumTemperature,
    weatherDescription = weatherConditions.firstOrNull()?.conditionDescription ?: "",
    weatherIconCode = weatherConditions.firstOrNull()?.iconCode ?: "",
    windSpeed = wind.speedInMetersPerSecond,
    windDirectionDegrees = wind.directionInDegrees,
    humidityPercentage = mainMetrics.humidityPercentage,
    visibilityInMeters = visibilityInMeters,
    atmosphericPressure = mainMetrics.atmosphericPressure,
    cityName = cityName,
    countryCode = systemInfo.countryCode
)

fun ForecastResponseDto.toForecastModel(): ForecastModel {
    val dailyGroups = forecastItems
        .groupBy { it.dateTimeText.substring(0, 10) }

    val dailyForecasts = dailyGroups.map { (date, items) ->
        DailyForecastModel(
            dayName = date.toDayName(),
            date = date.toFormattedDate(),
            highTemperature = items.maxOf { it.mainMetrics.maximumTemperature },
            lowTemperature = items.minOf { it.mainMetrics.minimumTemperature },
            weatherDescription = items.first().weatherConditions.firstOrNull()?.conditionDescription ?: "",
            weatherIconCode = items.first().weatherConditions.firstOrNull()?.iconCode ?: "",
            hourlyForecasts = items.map { it.toHourlyForecastModel() }
        )
    }

    return ForecastModel(
        cityName = city.cityName,
        countryCode = city.countryCode,
        dailyForecasts = dailyForecasts
    )
}

fun WeatherModel.toWeatherEntity(cacheKey: String) = WeatherEntity(
    cacheKey = cacheKey,
    cityName = cityName,
    countryCode = countryCode,
    temperature = temperature,
    feelsLikeTemperature = feelsLikeTemperature,
    minimumTemperature = minimumTemperature,
    maximumTemperature = maximumTemperature,
    weatherDescription = weatherDescription,
    weatherIconCode = weatherIconCode,
    windSpeed = windSpeed,
    windDirectionDegrees = windDirectionDegrees,
    humidityPercentage = humidityPercentage,
    visibilityInMeters = visibilityInMeters,
    atmosphericPressure = atmosphericPressure,
    lastUpdated = System.currentTimeMillis()
)

fun WeatherEntity.toWeatherModel() = WeatherModel(
    cityName = cityName,
    countryCode = countryCode,
    temperature = temperature,
    feelsLikeTemperature = feelsLikeTemperature,
    minimumTemperature = minimumTemperature,
    maximumTemperature = maximumTemperature,
    weatherDescription = weatherDescription,
    weatherIconCode = weatherIconCode,
    windSpeed = windSpeed,
    windDirectionDegrees = windDirectionDegrees,
    humidityPercentage = humidityPercentage,
    visibilityInMeters = visibilityInMeters,
    atmosphericPressure = atmosphericPressure
)

fun ForecastModel.toForecastEntity(cacheKey: String) = ForecastEntity(
    cacheKey = cacheKey,
    cityName = cityName,
    countryCode = countryCode,
    dailyForecasts = dailyForecasts,
    lastUpdated = System.currentTimeMillis()
)

fun ForecastEntity.toForecastModel() = ForecastModel(
    cityName = cityName,
    countryCode = countryCode,
    dailyForecasts = dailyForecasts
)

private fun ForecastItemDto.toHourlyForecastModel() = HourlyForecastModel(
    time = dateTimeText.substring(11, 16),
    weatherIconCode = weatherConditions.firstOrNull()?.iconCode ?: "",
    temperature = mainMetrics.temperature
)

private fun String.toDayName(): String {
    return try {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
        SimpleDateFormat("EEEE", Locale.getDefault()).format(date ?: Date())
    } catch (_: Exception) { this }
}

private fun String.toFormattedDate(): String {
    return try {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
        SimpleDateFormat("MMM dd", Locale.getDefault()).format(date ?: Date())
    } catch (_: Exception) { this }
}