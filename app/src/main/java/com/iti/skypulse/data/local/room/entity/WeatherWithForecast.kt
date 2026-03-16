package com.iti.skypulse.data.local.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherWithForecast(
    @Embedded val weather: WeatherEntity,
    @Relation(
        parentColumn = "cacheKey",
        entityColumn = "cacheKey"
    )
    val forecast: ForecastEntity
)