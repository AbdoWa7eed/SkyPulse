package com.iti.skypulse.data.local.datasource.alerts

import com.iti.skypulse.data.local.room.dao.WeatherAlertDao
import com.iti.skypulse.data.local.room.entity.WeatherAlertEntity

class WeatherAlertLocalDataSourceImpl(
    private val weatherAlertDao: WeatherAlertDao
) : WeatherAlertLocalDataSource {

    override fun getAll() = weatherAlertDao.getAll()

    override suspend fun insert(alert: WeatherAlertEntity) = weatherAlertDao.insert(alert)

    override suspend fun delete(id: String) = weatherAlertDao.delete(id)

    override suspend fun setEnabled(id: String, enabled: Boolean) = weatherAlertDao.setEnabled(id, enabled)

    override suspend fun getById(id: String) = weatherAlertDao.getById(id)
}