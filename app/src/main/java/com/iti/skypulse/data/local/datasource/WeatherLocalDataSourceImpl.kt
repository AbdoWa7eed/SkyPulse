package com.iti.skypulse.data.local.datasource

import com.iti.skypulse.data.local.room.dao.WeatherDao
import com.iti.skypulse.data.local.room.entity.ForecastEntity
import com.iti.skypulse.data.local.room.entity.WeatherEntity

class WeatherLocalDataSourceImpl(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {

    override suspend fun getWeather(cityName: String): WeatherEntity? =
        weatherDao.getWeather(cityName)


    override suspend fun saveWeather(weather: WeatherEntity) = weatherDao.saveWeather(weather)


    override suspend fun getForecast(cityName: String): ForecastEntity? =
        weatherDao.getForecast(cityName)

    override suspend fun saveForecast(forecast: ForecastEntity) = weatherDao.saveForecast(forecast)

    override suspend fun clearAll() {
        weatherDao.clearWeather()
        weatherDao.clearForecast()
    }

    override fun getFavorites() = weatherDao.getFavorites()
    override suspend fun markAsFavorite(cacheKey: String) = weatherDao.markAsFavorite(cacheKey)
    override suspend fun unmarkAsFavorite(cacheKey: String) = weatherDao.unmarkAsFavorite(cacheKey)
}