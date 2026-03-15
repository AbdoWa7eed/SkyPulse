package com.iti.skypulse.data.repository.alerts

import com.iti.skypulse.data.model.alert.WeatherAlert
import kotlinx.coroutines.flow.Flow

interface WeatherAlertRepository {
    fun getAlerts(): Flow<List<WeatherAlert>>
    suspend fun addAlert(alert: WeatherAlert)
    suspend fun deleteAlert(id: String)
    suspend fun toggleAlert(id: String, enabled: Boolean) : Result<Unit>
    suspend fun getAlertById(id: String): WeatherAlert?
}