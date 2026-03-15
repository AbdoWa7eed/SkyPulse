package com.iti.skypulse.core.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.iti.skypulse.data.model.alert.WeatherAlert

class WeatherAlertScheduler(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(alert: WeatherAlert) {
        if (alert.scheduledTime <= System.currentTimeMillis()) return

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alert.scheduledTime,
                buildPendingIntent(alert.id)
            )
        } catch (e: SecurityException) {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alert.scheduledTime,
                buildPendingIntent(alert.id)
            )
        }
    }

    fun cancel(alertId: String) {
        alarmManager.cancel(buildPendingIntent(alertId))
    }

    private fun buildPendingIntent(alertId: String): PendingIntent {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra(AlarmBroadcastReceiver.EXTRA_ALERT_ID, alertId)
        }
        return PendingIntent.getBroadcast(
            context,
            alertId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}