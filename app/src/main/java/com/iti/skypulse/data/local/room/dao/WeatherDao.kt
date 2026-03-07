package com.iti.skypulse.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iti.skypulse.data.local.room.entity.ForecastEntity
import com.iti.skypulse.data.local.room.entity.WeatherEntity

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather WHERE cityName = :cityName")
    suspend fun getWeather(cityName: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(weather: WeatherEntity)

    @Query("DELETE FROM weather WHERE cityName = :cityName")
    suspend fun deleteWeather(cityName: String)

    @Query("SELECT * FROM forecast WHERE cityName = :cityName")
    suspend fun getForecast(cityName: String): ForecastEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveForecast(forecast: ForecastEntity)

    @Query("DELETE FROM forecast WHERE cityName = :cityName")
    suspend fun deleteForecast(cityName: String)
}