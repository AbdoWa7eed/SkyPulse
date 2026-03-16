package com.iti.skypulse.core.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.iti.skypulse.SkyPulseApp
import com.iti.skypulse.di.ServiceLocator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        val app = context.applicationContext as SkyPulseApp

        app.appScope.launch {
            val repository = ServiceLocator.weatherAlertRepository
            val scheduler = ServiceLocator.weatherAlertScheduler

            repository.getAlerts().first().forEach { alert ->
                if (!alert.isEnabled) return@forEach

                if (alert.scheduledTime > System.currentTimeMillis()) {
                    scheduler.schedule(alert)
                } else {
                    repository.toggleAlert(alert.id, false)
                }
            }
        }
    }
}