package com.iti.skypulse.data.repository

import com.iti.skypulse.core.error.AppException
import com.iti.skypulse.core.network.ConnectivityHelper
import com.iti.skypulse.data.local.datasource.WeatherLocalDataSource
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.data.model.ForecastModel
import com.iti.skypulse.data.model.GeoPlace
import com.iti.skypulse.data.model.WeatherModel
import com.iti.skypulse.data.model.mapper.toForecastEntity
import com.iti.skypulse.data.model.mapper.toForecastModel
import com.iti.skypulse.data.model.mapper.toGeoPlace
import com.iti.skypulse.data.model.mapper.toWeatherEntity
import com.iti.skypulse.data.model.mapper.toWeatherModel
import com.iti.skypulse.data.remote.datasource.WeatherRemoteDataSource
import kotlinx.coroutines.flow.first

private const val CACHE_EXPIRY_MS = 30 * 60 * 1000L

class WeatherRepositoryImpl(
    private val appPreferences: AppPreferences,
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource,
    private val connectivityHelper: ConnectivityHelper
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Result<WeatherModel> {
        return runCatching {
            val cacheKey = buildCacheKey(latitude, longitude)
            val cached = localDataSource.getWeather(cacheKey)

            if (cached != null && !isCacheExpired(cached.lastUpdated)) {
                cached.toWeatherModel()
            } else if (connectivityHelper.isOnline()) {
                val fresh = remoteDataSource.getCurrentWeather(latitude, longitude).toWeatherModel()
                localDataSource.saveWeather(fresh.toWeatherEntity(cacheKey))
                fresh
            } else cached?.toWeatherModel() ?: throw AppException.NoCacheException()
        }
    }

    override suspend fun getFiveDayForecast(
        latitude: Double,
        longitude: Double
    ): Result<ForecastModel> {
        return runCatching {
            val cacheKey = buildCacheKey(latitude, longitude)
            val cached = localDataSource.getForecast(cacheKey)

            if (cached != null && !isCacheExpired(cached.lastUpdated)) {
                cached.toForecastModel()
            } else if (connectivityHelper.isOnline()) {
                val fresh = remoteDataSource.getFiveDayForecast(latitude, longitude).toForecastModel()
                localDataSource.saveForecast(fresh.toForecastEntity(cacheKey))
                fresh
            } else cached?.toForecastModel() ?: throw AppException.NoCacheException()
        }
    }

    override suspend fun searchPlaces(query: String): Result<List<GeoPlace>>{
        return runCatching {
            val langCode = appPreferences.language.first().code
            remoteDataSource.searchPlaces(query).map { dto -> dto.toGeoPlace(langCode)}
        }
    }

    private fun isCacheExpired(lastUpdated: Long): Boolean {
        return System.currentTimeMillis() - lastUpdated > CACHE_EXPIRY_MS
    }

    private suspend fun buildCacheKey(latitude: Double, longitude: Double): String {
        val lang = appPreferences.language.first().code
        return "${"%.2f".format(latitude)},${"%.2f".format(longitude)},$lang"
    }
}