package com.iti.skypulse.data.repository.alerts

import com.iti.skypulse.core.error.AppException
import com.iti.skypulse.core.scheduler.WeatherAlertScheduler
import com.iti.skypulse.data.local.datasource.alerts.WeatherAlertLocalDataSource
import com.iti.skypulse.data.model.alert.WeatherAlert
import com.iti.skypulse.data.model.alert.mapper.toEntity
import com.iti.skypulse.data.model.alert.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherAlertRepositoryImpl(
    private val localDataSource: WeatherAlertLocalDataSource,
    private val scheduler: WeatherAlertScheduler
) : WeatherAlertRepository {

    override fun getAlerts(): Flow<List<WeatherAlert>> =
        localDataSource.getAll().map { list -> list.map { it.toModel() } }

    override suspend fun addAlert(alert: WeatherAlert) {
        localDataSource.insert(alert.toEntity())
        scheduler.schedule(alert)
    }

    override suspend fun deleteAlert(id: String) {
        localDataSource.delete(id)
        scheduler.cancel(id)
    }

    override suspend fun toggleAlert(id: String, enabled: Boolean): Result<Unit> {
        return runCatching {
            localDataSource.setEnabled(id, enabled)
            if (!enabled) {
                scheduler.cancel(id)
            } else {
                val alert = localDataSource.getById(id)?.toModel() ?: return@runCatching
                val now = System.currentTimeMillis()
                when {
                    alert.endTime <= now -> {
                        localDataSource.setEnabled(id, false)
                        throw AppException.AlertExpiredException()
                    }
                    alert.scheduledTime <= now -> {
                        localDataSource.setEnabled(id, false)
                        throw AppException.AlertExpiredException()
                    }
                    else -> scheduler.schedule(alert)
                }
            }
        }
    }

    override suspend fun getAlertById(id: String): WeatherAlert? =
        localDataSource.getById(id)?.toModel()
}