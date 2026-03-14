package com.iti.skypulse.ui.alarms

import com.iti.skypulse.data.model.AlertNotificationType
import com.iti.skypulse.data.model.WeatherAlert
import com.iti.skypulse.data.model.WeatherAlertType
import java.util.Calendar

data class AlertFormState(
    val type: WeatherAlertType? = null,
    val notificationType: AlertNotificationType = AlertNotificationType.NOTIFICATION,
    val dateMillis: Long? = null,
    val hour: Int? = null,
    val minute: Int? = null
) {
    val isValid get() = type != null && dateMillis != null && hour != null

    fun toScheduledTime(): Long = Calendar.getInstance().apply {
        timeInMillis = dateMillis!!
        set(Calendar.HOUR_OF_DAY, hour!!)
        set(Calendar.MINUTE, minute ?: 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    val isInFuture get(): Boolean {
        if (dateMillis == null || hour == null) return false
        return Calendar.getInstance().apply {
            timeInMillis = dateMillis
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute ?: 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis > System.currentTimeMillis()
    }

    fun toWeatherAlert(): WeatherAlert = WeatherAlert(
        id = System.currentTimeMillis().toString(),
        type = type!!,
        notificationType = notificationType,
        isEnabled = true,
        scheduledTime = toScheduledTime()
    )
}