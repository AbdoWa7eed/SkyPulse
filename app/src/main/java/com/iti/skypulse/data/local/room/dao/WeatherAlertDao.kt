package com.iti.skypulse.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iti.skypulse.data.local.room.entity.WeatherAlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherAlertDao {

    @Query("SELECT * FROM weather_alerts ORDER BY scheduledTime ASC")
    fun getAll(): Flow<List<WeatherAlertEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alert: WeatherAlertEntity)

    @Query("DELETE FROM weather_alerts WHERE id = :id")
    suspend fun delete(id: String)

    @Query("UPDATE weather_alerts SET isEnabled = :enabled WHERE id = :id")
    suspend fun setEnabled(id: String, enabled: Boolean)

    @Query("SELECT * FROM weather_alerts WHERE id = :id")
    suspend fun getById(id: String): WeatherAlertEntity?
}