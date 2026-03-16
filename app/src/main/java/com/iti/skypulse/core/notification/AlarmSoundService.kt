package com.iti.skypulse.core.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.iti.skypulse.R

class AlarmSoundService : Service() {

    companion object {
        const val ACTION_START = "com.iti.skypulse.ALARM_SOUND_START"
        const val ACTION_STOP = "com.iti.skypulse.ALARM_SOUND_STOP"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_MESSAGE = "extra_message"
        const val FOREGROUND_ID = 9999
        const val CHANNEL_ALARM = "weather_alarms_v2"

        fun startIntent(
            context: Context,
            title: String,
            message: String,
        ) = Intent(context, AlarmSoundService::class.java).apply {
            action = ACTION_START
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_MESSAGE, message)
        }

        fun stopIntent(context: Context) =
            Intent(context, AlarmSoundService::class.java).apply { action = ACTION_STOP }
    }


    private var ringtone: Ringtone? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                val title = intent.getStringExtra(EXTRA_TITLE) ?: ""
                val message = intent.getStringExtra(EXTRA_MESSAGE) ?: ""
                startAlarm(title, message)
            }

            ACTION_STOP -> stopAlarm()
        }
        return START_STICKY
    }

    private fun startAlarm(title: String, message: String) {
        playSound()
        startForeground(FOREGROUND_ID, buildNotification(title, message))
    }

    private fun buildNotification(
        title: String,
        message: String,
    ): Notification {
        createChannel()

        val dismissPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent(this),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val collapsedView = RemoteViews(packageName, R.layout.notification_alarm_collapsed).apply {
            setTextViewText(R.id.tv_title, title)
            setTextViewText(R.id.tv_message, message)
            setOnClickPendingIntent(R.id.btn_dismiss, dismissPendingIntent)
        }

        val expandedView = RemoteViews(packageName, R.layout.notification_alarm_expanded).apply {
            setTextViewText(R.id.tv_title, title)
            setTextViewText(R.id.tv_message, message)
            setOnClickPendingIntent(R.id.btn_dismiss, dismissPendingIntent)
        }

        return NotificationCompat.Builder(this, CHANNEL_ALARM)
            .setSmallIcon(R.drawable.app_logo)
            .setCustomContentView(collapsedView)
            .setCustomBigContentView(expandedView)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .setAutoCancel(false)
            .build()
            .also { it.flags = it.flags or Notification.FLAG_NO_CLEAR }
    }

    private fun playSound() {
        runCatching {
            ringtone?.stop()
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            ringtone = RingtoneManager.getRingtone(this, uri).also {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) it.isLooping = true
                it.play()
            }
        }
    }

    private fun stopAlarm() {
        ringtone?.stop()
        ringtone = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        ringtone?.stop()
        ringtone = null
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createChannel() {
        val channel = NotificationChannel(
            CHANNEL_ALARM,
            "Weather Alarms",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Weather condition alarms"
            enableVibration(true)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)
    }

}