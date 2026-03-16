package com.iti.skypulse.core.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DismissAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.stopService(AlarmSoundService.stopIntent(context))
    }

}