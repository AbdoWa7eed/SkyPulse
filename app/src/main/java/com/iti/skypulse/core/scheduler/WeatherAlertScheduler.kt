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
        scheduleExact(alert.scheduledTime, buildPendingIntent(alert.id, ACTION_FIRE))
        scheduleExact(alert.endTime, buildPendingIntent(alert.id, ACTION_EXPIRE))
    }

    fun cancel(alertId: String) {
        alarmManager.cancel(buildPendingIntent(alertId, ACTION_FIRE))
        alarmManager.cancel(buildPendingIntent(alertId, ACTION_EXPIRE))
    }

    private fun scheduleExact(timeMillis: Long, pendingIntent: PendingIntent) {
        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeMillis,
                pendingIntent
            )
        } catch (_: SecurityException) {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeMillis,
                pendingIntent
            )
        }
    }

    private fun buildPendingIntent(alertId: String, action: String): PendingIntent {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            this.action = action
            putExtra(AlarmBroadcastReceiver.EXTRA_ALERT_ID, alertId)
        }
        val requestCode = if (action == ACTION_FIRE) alertId.hashCode()
        else alertId.hashCode() + 1
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        const val ACTION_FIRE   = "com.iti.skypulse.ACTION_ALERT_FIRE"
        const val ACTION_EXPIRE = "com.iti.skypulse.ACTION_ALERT_EXPIRE"
    }
}