package com.iti.skypulse.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iti.skypulse.data.model.DailyForecastModel

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey
    val cacheKey: String,
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean = false,
    val countryCode: String,
    val dailyForecasts: List<DailyForecastModel>,
    val lastUpdated: Long = System.currentTimeMillis()
)