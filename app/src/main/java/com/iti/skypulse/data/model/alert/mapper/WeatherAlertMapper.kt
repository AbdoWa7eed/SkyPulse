package com.iti.skypulse.data.model.alert.mapper

import com.iti.skypulse.data.local.room.entity.WeatherAlertEntity
import com.iti.skypulse.data.model.alert.WeatherAlert

fun WeatherAlertEntity.toModel() = WeatherAlert(
    id = id,
    type = type,
    notificationType = notificationType,
    isEnabled = isEnabled,
    scheduledTime = scheduledTime,
    endTime = endTime,
)

fun WeatherAlert.toEntity() = WeatherAlertEntity(
    id = id,
    type = type,
    notificationType = notificationType,
    isEnabled = isEnabled,
    scheduledTime = scheduledTime,
    endTime = endTime,
)