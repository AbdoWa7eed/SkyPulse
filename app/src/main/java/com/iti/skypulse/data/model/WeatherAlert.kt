package com.iti.skypulse.data.model

enum class WeatherAlertType { RAIN, SNOW, FOG, HIGH_TEMP, HIGH_WIND }
enum class AlertNotificationType { NOTIFICATION, ALARM }

data class WeatherAlert(
    val id: String,
    val type: WeatherAlertType,
    val notificationType: AlertNotificationType,
    val isEnabled: Boolean,
    val scheduledTime: Long
)