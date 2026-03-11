package com.iti.skypulse.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iti.skypulse.data.local.room.entity.ForecastEntity
import com.iti.skypulse.data.local.room.entity.WeatherEntity

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather WHERE cacheKey = :cacheKey")
    suspend fun getWeather(cacheKey: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(weather: WeatherEntity)

    @Query("DELETE FROM weather WHERE cacheKey = :cacheKey")
    suspend fun deleteWeather(cacheKey: String)

    @Query("SELECT * FROM forecast WHERE cacheKey = :cacheKey")
    suspend fun getForecast(cacheKey: String): ForecastEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveForecast(forecast: ForecastEntity)

    @Query("DELETE FROM forecast WHERE cacheKey = :cacheKey")
    suspend fun deleteForecast(cacheKey: String)

    @Query("DELETE FROM weather")
    suspend fun clearWeather()

    @Query("DELETE FROM forecast")
    suspend fun clearForecast()
}