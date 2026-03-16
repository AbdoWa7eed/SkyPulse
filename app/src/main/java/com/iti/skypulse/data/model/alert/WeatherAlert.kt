package com.iti.skypulse.data.model.alert

enum class WeatherAlertType { RAIN, SNOW, FOG, HIGH_TEMP, HIGH_WIND, CLEAR }
enum class AlertNotificationType { NOTIFICATION, ALARM }

data class WeatherAlert(
    val id: String,
    val type: WeatherAlertType,
    val notificationType: AlertNotificationType,
    val isEnabled: Boolean,
    val scheduledTime: Long,
    val endTime: Long

)

fun WeatherAlertType.matches(
    conditionCode: Int, tempCelsius: Double, windSpeedMs: Double
): Boolean = when (this) {
    WeatherAlertType.RAIN -> conditionCode in 200..232 || conditionCode in 300..321 || conditionCode in 500..531
    WeatherAlertType.SNOW -> conditionCode in 600..622 || conditionCode == 511
    WeatherAlertType.FOG -> conditionCode in 700..781
    WeatherAlertType.HIGH_WIND -> windSpeedMs >= 10.0
    WeatherAlertType.HIGH_TEMP -> tempCelsius >= 40.0
    WeatherAlertType.CLEAR -> conditionCode == 800 || conditionCode == 801
}