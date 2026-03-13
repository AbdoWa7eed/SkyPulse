package com.iti.skypulse.data.model.mapper

import com.iti.skypulse.core.extensions.toDayName
import com.iti.skypulse.core.extensions.toFormattedDate
import com.iti.skypulse.core.extensions.toLocalizedTime
import com.iti.skypulse.data.local.room.entity.ForecastEntity
import com.iti.skypulse.data.local.room.entity.WeatherEntity
import com.iti.skypulse.data.local.room.entity.WeatherWithForecast
import com.iti.skypulse.data.model.DailyForecastModel
import com.iti.skypulse.data.model.FavoriteWeather
import com.iti.skypulse.data.model.ForecastModel
import com.iti.skypulse.data.model.GeoPlace
import com.iti.skypulse.data.model.HourlyForecastModel
import com.iti.skypulse.data.model.WeatherModel
import com.iti.skypulse.data.remote.dto.ForecastItemDto
import com.iti.skypulse.data.remote.dto.ForecastResponseDto
import com.iti.skypulse.data.remote.dto.GeoPlaceDto
import com.iti.skypulse.data.remote.dto.WeatherResponseDto
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
    latitude = coordinates.latitude,
    longitude = coordinates.longitude,
    countryCode = systemInfo.countryCode
)

fun ForecastResponseDto.toForecastModel(): ForecastModel {
    val dailyGroups = forecastItems
        .groupBy { it.dateTimeText.substring(0, 10) }

    val dailyForecasts = dailyGroups
        .entries
        .sortedBy { it.key }
        .map { (date, items) ->
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
        latitude = city.coordinates.latitude,
        longitude = city.coordinates.longitude,
        dailyForecasts = dailyForecasts,
    )
}

fun WeatherModel.toWeatherEntity(cacheKey: String, lang: String) = WeatherEntity(
    cacheKey = cacheKey,
    lang = lang,
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
    longitude = longitude,
    latitude = latitude,
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
    longitude = longitude,
    latitude = latitude,
    atmosphericPressure = atmosphericPressure
)

fun ForecastModel.toForecastEntity(cacheKey: String, lang: String) = ForecastEntity(
    cacheKey = cacheKey,
    lang = lang,
    cityName = cityName,
    countryCode = countryCode,
    dailyForecasts = dailyForecasts,
    longitude = longitude,
    latitude = latitude,
    lastUpdated = System.currentTimeMillis()
)

fun ForecastEntity.toForecastModel() = ForecastModel(
    cityName = cityName,
    countryCode = countryCode,
    longitude = longitude,
    latitude = latitude,
    dailyForecasts = dailyForecasts
)

fun WeatherWithForecast.toFavoriteModel() = FavoriteWeather(
    weather = weather.toWeatherModel(),
    forecast = forecast.toForecastModel()
)

private fun ForecastItemDto.toHourlyForecastModel() = HourlyForecastModel(
    time = dateTimeText.toLocalizedTime(),
    weatherIconCode = weatherConditions.firstOrNull()?.iconCode ?: "",
    temperature = mainMetrics.temperature
)


fun GeoPlaceDto.toGeoPlace(langCode: String): GeoPlace {
    return GeoPlace(
        name = localNames?.get(langCode) ?: name,
        latitude = lat,
        longitude = lng,
        country = country,
        state   = state
    )
}
