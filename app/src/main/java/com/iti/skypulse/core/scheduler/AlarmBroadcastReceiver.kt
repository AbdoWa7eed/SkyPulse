package com.iti.skypulse.core.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.BackoffPolicy
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.iti.skypulse.worker.WeatherAlertWorker
import java.util.concurrent.TimeUnit

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val alertId = intent.getStringExtra(EXTRA_ALERT_ID) ?: return

        when (intent.action) {
            WeatherAlertScheduler.ACTION_FIRE -> handleFire(context, alertId)
            WeatherAlertScheduler.ACTION_EXPIRE -> handleExpire(context, alertId)
        }
    }

    private fun handleFire(context: Context, alertId: String) {
        enqueueWork(context, alertId, WeatherAlertWorker.ACTION_FIRE, alertId)
    }

    private fun handleExpire(context: Context, alertId: String) {
        enqueueWork(context, alertId, WeatherAlertWorker.ACTION_EXPIRE, "expire_$alertId")
    }

    private fun enqueueWork(context: Context, alertId: String, action: String, uniqueName: String) {
        val request = OneTimeWorkRequestBuilder<WeatherAlertWorker>()
            .setInputData(
                Data.Builder()
                    .putString(WeatherAlertWorker.KEY_ALERT_ID, alertId)
                    .putString(WeatherAlertWorker.KEY_ACTION, action)
                    .build()
            ).setBackoffCriteria(
                BackoffPolicy.LINEAR, BACK_OFF_DELAY, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(context)
            .enqueueUniqueWork(uniqueName, ExistingWorkPolicy.REPLACE, request)
    }

    companion object {
        const val EXTRA_ALERT_ID = "alert_id"
        const val BACK_OFF_DELAY = 30L
    }
}