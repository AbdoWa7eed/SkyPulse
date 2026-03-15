package com.iti.skypulse.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.UnitConverter
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.data.model.alert.matches
import com.iti.skypulse.di.ServiceLocator
import kotlinx.coroutines.flow.first

class WeatherAlertWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {
        val alertId = inputData.getString(KEY_ALERT_ID)
            ?: return Result.failure()

        val alertRepository = ServiceLocator.weatherAlertRepository
        val weatherRepository = ServiceLocator.weatherRepository
        val appPreferences = ServiceLocator.settingsRepository
        val notificationManager = ServiceLocator.weatherNotificationManager
        val langCode = appPreferences.language.first().code


        val alert = alertRepository.getAlertById(alertId)
            ?: return Result.success()

        if (!alert.isEnabled) return Result.success()

        val location = appPreferences.savedLocation.first()
            ?: run {
                notificationManager.fireError(alert, langCode)
                alertRepository.toggleAlert(alertId, false)
                return Result.failure()
            }

        val weatherResult = weatherRepository.getCurrentWeather(location.lat, location.lng)

        weatherResult.fold(
            onSuccess = { weather ->
                val tempCelsius = UnitConverter.formatTemp(weather.temperature, TempUnit.CELSIUS)
                val windMs =  UnitConverter
                    .formatWind(weather.temperature, WindUnit.METERS_PER_SECOND)
                val matched = alert.type.matches(
                    weather.conditionCode,
                    tempCelsius.numericValue,
                    windMs.numericValue)
                notificationManager.fireAlert(alert, matched, langCode)
                alertRepository.toggleAlert(alertId, false)
            },
            onFailure = {
                if (runAttemptCount < MAX_RETRIES) return Result.retry()
                notificationManager.fireError(alert, langCode)
                alertRepository.toggleAlert(alertId, false)
            }
        )

        return Result.success()
    }

    companion object {
        const val KEY_ALERT_ID = "alert_id"
        private const val MAX_RETRIES = 3
    }
}