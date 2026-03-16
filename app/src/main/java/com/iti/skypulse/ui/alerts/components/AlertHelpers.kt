package com.iti.skypulse.ui.alerts.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material.icons.rounded.AcUnit
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.BeachAccess
import androidx.compose.material.icons.rounded.BlurOn
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.iti.skypulse.R
import com.iti.skypulse.data.model.alert.AlertNotificationType
import com.iti.skypulse.data.model.alert.WeatherAlertType

fun alertIcon(type: WeatherAlertType): ImageVector = when (type) {
    WeatherAlertType.HIGH_WIND -> Icons.Rounded.Air
    WeatherAlertType.RAIN      -> Icons.Rounded.BeachAccess
    WeatherAlertType.SNOW      -> Icons.Rounded.AcUnit
    WeatherAlertType.FOG       -> Icons.Rounded.BlurOn
    WeatherAlertType.HIGH_TEMP -> Icons.Rounded.Thermostat
    WeatherAlertType.CLEAR     -> Icons.Rounded.WbSunny
}

@Composable
fun alertLabel(type: WeatherAlertType): String = stringResource(
    when (type) {
        WeatherAlertType.RAIN      -> R.string.alert_rain
        WeatherAlertType.SNOW      -> R.string.alert_snow
        WeatherAlertType.FOG       -> R.string.alert_fog
        WeatherAlertType.HIGH_TEMP -> R.string.alert_high_temp
        WeatherAlertType.HIGH_WIND -> R.string.alert_high_wind
        WeatherAlertType.CLEAR     -> R.string.alert_type_clear
    }
)

fun alertNotificationIcon(type: AlertNotificationType): ImageVector = when (type) {
    AlertNotificationType.NOTIFICATION -> Icons.Rounded.Notifications
    AlertNotificationType.ALARM -> Icons.AutoMirrored.Rounded.VolumeUp
}

@Composable
fun alertNotificationLabel(type: AlertNotificationType): String = stringResource(
    when (type) {
        AlertNotificationType.NOTIFICATION -> R.string.alert_type_notification
        AlertNotificationType.ALARM -> R.string.alert_type_alarm
    }
)