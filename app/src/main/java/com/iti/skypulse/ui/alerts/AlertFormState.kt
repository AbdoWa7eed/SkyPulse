package com.iti.skypulse.ui.alerts

import com.iti.skypulse.core.extensions.toEpoch
import com.iti.skypulse.data.model.alert.AlertNotificationType
import com.iti.skypulse.data.model.alert.WeatherAlert
import com.iti.skypulse.data.model.alert.WeatherAlertType
import java.util.Calendar

data class AlertFormState(
    val id: String? = null,
    val type: WeatherAlertType? = null,
    val notificationType: AlertNotificationType = AlertNotificationType.NOTIFICATION,
    val dateMillis: Long? = null,
    val hour: Int? = null,
    val minute: Int? = null,
    val endDateMillis: Long? = null,
    val endHour: Int? = null,
    val endMinute: Int? = null
) {
    companion object {
        fun fromWeatherAlert(alert: WeatherAlert): AlertFormState {
            val startCal = Calendar.getInstance().apply { timeInMillis = alert.scheduledTime }
            val endCal = Calendar.getInstance().apply { timeInMillis = alert.endTime }
            return AlertFormState(
                id = alert.id,
                type = alert.type,
                notificationType = alert.notificationType,
                dateMillis = alert.scheduledTime,
                hour = startCal.get(Calendar.HOUR_OF_DAY),
                minute = startCal.get(Calendar.MINUTE),
                endDateMillis = alert.endTime,
                endHour = endCal.get(Calendar.HOUR_OF_DAY),
                endMinute = endCal.get(Calendar.MINUTE)
            )
        }
    }

    val isValid
        get() = type != null
                && dateMillis != null && hour != null
                && endDateMillis != null && endHour != null

    val isInFuture
        get(): Boolean {
            if (dateMillis == null || hour == null) return false
            return toScheduledTime() > System.currentTimeMillis()
        }

    val isEndAfterStart
        get(): Boolean {
            if (endDateMillis == null || endHour == null) return false
            return toEndTime() > toScheduledTime()
        }

    fun toScheduledTime(): Long = dateMillis!!.toEpoch(hour!!, minute ?: 0)

    fun toEndTime(): Long = endDateMillis!!.toEpoch(endHour!!, endMinute ?: 0)

    fun toWeatherAlert(): WeatherAlert = WeatherAlert(
        id = id ?: System.currentTimeMillis().toString(),
        type = type!!,
        notificationType = notificationType,
        isEnabled = true,
        scheduledTime = toScheduledTime(),
        endTime = toEndTime()
    )

}