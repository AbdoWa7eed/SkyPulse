package com.iti.skypulse.data.local.datasource.alerts

import com.iti.skypulse.data.local.room.entity.WeatherAlertEntity
import kotlinx.coroutines.flow.Flow

interface WeatherAlertLocalDataSource {
    fun getAll(): Flow<List<WeatherAlertEntity>>
    suspend fun insert(alert: WeatherAlertEntity)
    suspend fun delete(id: String)
    suspend fun setEnabled(id: String, enabled: Boolean)
    suspend fun getById(id: String): WeatherAlertEntity?
}