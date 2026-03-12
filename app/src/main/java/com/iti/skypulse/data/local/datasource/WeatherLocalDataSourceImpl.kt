package com.iti.skypulse.data.local.datasource

import com.iti.skypulse.data.local.room.dao.WeatherDao
import com.iti.skypulse.data.local.room.entity.ForecastEntity
import com.iti.skypulse.data.local.room.entity.WeatherEntity

class WeatherLocalDataSourceImpl(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {

    override suspend fun getWeather(cacheKey: String): WeatherEntity? = weatherDao.getWeather(cacheKey)
    override suspend fun saveWeather(weather: WeatherEntity) = weatherDao.saveWeather(weather)
    override suspend fun deleteWeather(cacheKey: String) = weatherDao.deleteWeather(cacheKey)

    override suspend fun getForecast(cacheKey: String): ForecastEntity? = weatherDao.getForecast(cacheKey)
    override suspend fun saveForecast(forecast: ForecastEntity) = weatherDao.saveForecast(forecast)
    override suspend fun deleteForecast(cacheKey: String) = weatherDao.deleteForecast(cacheKey)

    override fun getFavorites() = weatherDao.getFavorites()
    override suspend fun markAsFavorite(cacheKey: String) = weatherDao.markAsFavorite(cacheKey)
    override suspend fun unmarkAsFavorite(cacheKey: String) = weatherDao.unmarkAsFavorite(cacheKey)
    override suspend fun isFavorite(cacheKey: String) = weatherDao.isFavorite(cacheKey)
}