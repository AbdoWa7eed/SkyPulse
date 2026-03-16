package com.iti.skypulse.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.iti.skypulse.data.local.room.entity.ForecastEntity
import com.iti.skypulse.data.local.room.entity.LatLngEntity
import com.iti.skypulse.data.local.room.entity.WeatherEntity
import com.iti.skypulse.data.local.room.entity.WeatherWithForecast
import kotlinx.coroutines.flow.Flow
@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather WHERE cacheKey = :cacheKey AND lang = :lang")
    suspend fun getWeather(cacheKey: String, lang: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(weather: WeatherEntity)

    @Query("DELETE FROM weather WHERE cacheKey = :cacheKey")
    suspend fun deleteWeather(cacheKey: String)

    @Query("SELECT * FROM forecast WHERE cacheKey = :cacheKey AND lang = :lang")
    suspend fun getForecast(cacheKey: String, lang: String): ForecastEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveForecast(forecast: ForecastEntity)

    @Query("DELETE FROM forecast WHERE cacheKey = :cacheKey")
    suspend fun deleteForecast(cacheKey: String)

    @Query("DELETE FROM weather")
    suspend fun clearWeather()

    @Query("DELETE FROM forecast")
    suspend fun clearForecast()

    @Transaction
    @Query("SELECT * FROM weather WHERE isFavorite = 1 AND lang = :lang")
    fun getFavorites(lang: String): Flow<List<WeatherWithForecast>>

    @Query("UPDATE weather SET isFavorite = 1 WHERE cacheKey = :cacheKey")
    suspend fun markAsFavorite(cacheKey: String)

    @Query("UPDATE weather SET isFavorite = 0 WHERE cacheKey = :cacheKey")
    suspend fun unmarkAsFavorite(cacheKey: String)

    @Query("SELECT isFavorite FROM weather WHERE cacheKey = :cacheKey")
    suspend fun isFavorite(cacheKey: String): Boolean


    @Query("SELECT latitude, longitude FROM weather WHERE isFavorite = 1 GROUP BY latitude, longitude")
    suspend fun getAllFavoriteLocations(): List<LatLngEntity>
}