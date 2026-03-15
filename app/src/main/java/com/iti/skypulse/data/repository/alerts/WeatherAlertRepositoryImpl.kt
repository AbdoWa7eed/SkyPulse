package com.iti.skypulse.data.repository.alerts

import com.iti.skypulse.data.local.datasource.WeatherLocalDataSource
import com.iti.skypulse.data.model.WeatherAlert
import kotlinx.coroutines.flow.Flow

class WeatherAlertRepositoryImpl(
    private val weatherAlertDataSource: WeatherLocalDataSource
) : WeatherAlertRepository {

    override fun getAlerts(): Flow<List<WeatherAlert>> {
        TODO("Return alerts flow from WeatherLocalDataSource (Room) to observe stored alerts")
    }

    override suspend fun addAlert(alert: WeatherAlert) {
        TODO("Save alert in local database and schedule corresponding WorkManager task")
    }

    override suspend fun deleteAlert(id: String) {
        TODO("Remove alert from database and cancel its scheduled WorkManager job")
    }

    override suspend fun toggleAlert(id: String, enabled: Boolean) {
        TODO("Update alert enabled state in database and enable/disable its WorkManager schedule")
    }

    override suspend fun getAlertById(id: String): WeatherAlert? {
        TODO("Retrieve specific alert from local database by id")
    }
}