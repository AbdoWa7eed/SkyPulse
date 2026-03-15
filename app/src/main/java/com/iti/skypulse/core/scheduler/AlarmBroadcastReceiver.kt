package com.iti.skypulse.core.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.iti.skypulse.worker.WeatherAlertWorker

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val alertId = intent.getStringExtra(EXTRA_ALERT_ID) ?: return

        val request = OneTimeWorkRequestBuilder<WeatherAlertWorker>()
            .setInputData(
                Data.Builder()
                    .putString(WeatherAlertWorker.KEY_ALERT_ID, alertId)
                    .build()
            )
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(alertId, ExistingWorkPolicy.REPLACE, request)
    }

    companion object {
        const val EXTRA_ALERT_ID = "alert_id"
    }
}