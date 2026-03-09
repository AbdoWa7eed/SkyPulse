package com.iti.skypulse.data.local.datasource

import com.iti.skypulse.data.local.room.dao.WeatherDao
import com.iti.skypulse.data.local.room.entity.ForecastEntity
import com.iti.skypulse.data.local.room.entity.WeatherEntity

class WeatherLocalDataSourceImpl(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {

    override suspend fun getWeather(cityName: String): WeatherEntity? {
        return weatherDao.getWeather(cityName)
    }

    override suspend fun saveWeather(weather: WeatherEntity) {
        weatherDao.saveWeather(weather)
    }

    override suspend fun getForecast(cityName: String): ForecastEntity? {
        return weatherDao.getForecast(cityName)
    }

    override suspend fun saveForecast(forecast: ForecastEntity) {
        weatherDao.saveForecast(forecast)
    }

    override suspend fun clearAll() {
    }
}