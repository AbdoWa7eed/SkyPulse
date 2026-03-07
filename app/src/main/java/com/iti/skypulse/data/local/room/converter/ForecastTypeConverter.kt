package com.iti.skypulse.data.local.room.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iti.skypulse.data.model.DailyForecastModel

class ForecastTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromDailyForecastList(value: List<DailyForecastModel>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toDailyForecastList(value: String): List<DailyForecastModel> {
        return gson.fromJson(
            value,
            object : TypeToken<List<DailyForecastModel>>() {}.type
        )
    }
}