package com.iti.skypulse.data.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.iti.skypulse.data.local.room.converter.ForecastTypeConverter
import com.iti.skypulse.data.local.room.dao.WeatherAlertDao
import com.iti.skypulse.data.local.room.dao.WeatherDao
import com.iti.skypulse.data.local.room.entity.ForecastEntity
import com.iti.skypulse.data.local.room.entity.WeatherAlertEntity
import com.iti.skypulse.data.local.room.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class, ForecastEntity::class, WeatherAlertEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ForecastTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    abstract fun weatherAlertDao() : WeatherAlertDao

    companion object {
        private const val DATABASE_NAME = "skypulse_db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build().also { instance = it }
            }
        }
    }
}