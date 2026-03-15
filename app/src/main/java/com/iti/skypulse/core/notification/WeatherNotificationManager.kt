package com.iti.skypulse.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.iti.skypulse.MainActivity
import com.iti.skypulse.R
import com.iti.skypulse.core.extensions.withLocale
import com.iti.skypulse.data.model.alert.AlertNotificationType
import com.iti.skypulse.data.model.alert.WeatherAlert
import com.iti.skypulse.data.model.alert.WeatherAlertType

class WeatherNotificationManager(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    fun fireAlert(
        alert: WeatherAlert,
        matched: Boolean,
        lang: String
    ) {
        val localizedContext = context.withLocale(lang)

        val (title, message) = if (matched) {
            getAlertTitleAndMessage(alert.type, localizedContext)
        } else {
            Pair(
                localizedContext.getString(R.string.alert_notification_title_clear),
                localizedContext.getString(R.string.alert_notification_message_clear)
            )
        }

        when (alert.notificationType) {
            AlertNotificationType.NOTIFICATION -> showNotification(alert.id, title, message)
            AlertNotificationType.ALARM        -> showAlarm(alert.id, title, message)
        }
    }
    fun fireError(alert: WeatherAlert, lang: String) {
        val localizedContext = context.withLocale(lang)
        showNotification(
            id = alert.id,
            title = localizedContext.getString(R.string.alert_notification_error_title),
            message = localizedContext.getString(R.string.alert_notification_error_message)
        )
    }

    private fun getAlertTitleAndMessage(
        alertType: WeatherAlertType,
        localizedContext: Context
    ): Pair<String, String> = when (alertType) {
        WeatherAlertType.RAIN -> Pair(
            localizedContext.getString(R.string.alert_notification_title_rain),
            localizedContext.getString(R.string.alert_notification_message_rain)
        )
        WeatherAlertType.SNOW -> Pair(
            localizedContext.getString(R.string.alert_notification_title_snow),
            localizedContext.getString(R.string.alert_notification_message_snow)
        )
        WeatherAlertType.FOG -> Pair(
            localizedContext.getString(R.string.alert_notification_title_fog),
            localizedContext.getString(R.string.alert_notification_message_fog)
        )
        WeatherAlertType.HIGH_TEMP -> Pair(
            localizedContext.getString(R.string.alert_notification_title_high_temp),
            localizedContext.getString(R.string.alert_notification_message_high_temp)
        )
        WeatherAlertType.HIGH_WIND -> Pair(
            localizedContext.getString(R.string.alert_notification_title_high_wind),
            localizedContext.getString(R.string.alert_notification_message_high_wind)
        )
    }

    private fun showNotification(id: String, title: String, message: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_NOTIFICATION)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(openAppIntent())
            .build()

        notificationManager.notify(id.hashCode(), notification)
    }

    private fun showAlarm(id: String, title: String, message: String) {
        val intent = AlarmSoundService.startIntent(context, title, message, id.hashCode())
        context.startForegroundService(intent)
    }
    private fun openAppIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createNotificationChannel() {
        notificationManager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_NOTIFICATION,
                "Weather Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Weather condition notifications"
            }
        )
    }

    companion object {
        const val CHANNEL_NOTIFICATION = "weather_alerts"
    }
}