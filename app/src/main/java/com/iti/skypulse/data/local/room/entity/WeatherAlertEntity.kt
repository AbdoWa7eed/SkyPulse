package com.iti.skypulse.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iti.skypulse.data.model.AlertNotificationType
import com.iti.skypulse.data.model.WeatherAlertType

@Entity(tableName = "weather_alerts")
data class WeatherAlertEntity(
    @PrimaryKey
    val id: String,
    val type: WeatherAlertType,
    val notificationType: AlertNotificationType,
    val isEnabled: Boolean,
    val scheduledTime: Long
)