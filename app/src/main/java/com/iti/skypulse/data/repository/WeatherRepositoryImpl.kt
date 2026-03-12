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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val CACHE_EXPIRY_MS = 30 * 60 * 1000L

class WeatherRepositoryImpl(
    private val appPreferences: AppPreferences,
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource,
    private val connectivityHelper: ConnectivityHelper
) : WeatherRepository {

    override suspend fun getCurrentWeather(latitude: Double, longitude: Double): Result<WeatherModel> {
        return runCatching {
            val cacheKey = buildCacheKey(latitude, longitude)
            val cached = localDataSource.getWeather(cacheKey)
            when {
                cached != null && !isCacheExpired(cached.lastUpdated) -> cached.toWeatherModel()
                connectivityHelper.isOnline() -> fetchAndSaveWeather(latitude, longitude, cacheKey)
                else -> cached?.toWeatherModel() ?: throw AppException.NoCacheException()
            }
        }
    }

    override suspend fun getFiveDayForecast(latitude: Double, longitude: Double): Result<ForecastModel> {
        return runCatching {
            val cacheKey = buildCacheKey(latitude, longitude)
            val cached = localDataSource.getForecast(cacheKey)
            when {
                cached != null && !isCacheExpired(cached.lastUpdated) -> cached.toForecastModel()
                connectivityHelper.isOnline() -> fetchAndSaveForecast(latitude, longitude, cacheKey)
                else -> cached?.toForecastModel() ?: throw AppException.NoCacheException()
            }
        }
    }

    override suspend fun searchPlaces(query: String): Result<List<GeoPlace>>{
        return runCatching {
            val langCode = appPreferences.language.first().code
            remoteDataSource.searchPlaces(query).map { dto -> dto.toGeoPlace(langCode)}
        }
    }
    override suspend fun addFavorite(latitude: Double, longitude: Double): Result<Unit> {
        return runCatching {
            val cacheKey = buildCacheKey(latitude, longitude)
            when {
                localDataSource.getWeather(cacheKey) != null ->
                    localDataSource.markAsFavorite(cacheKey)
                connectivityHelper.isOnline() -> fetchAndSaveWeather(latitude, longitude, cacheKey, isFavorite = true)
                else -> throw AppException.NoInternetException()
            }
        }
    }
    override suspend fun removeFavorite(latitude: Double, longitude: Double) {
        localDataSource.unmarkAsFavorite(buildCacheKey(latitude, longitude))
    }

    override suspend fun refreshFavorite(latitude: Double, longitude: Double) {
        if (!connectivityHelper.isOnline()) return
        runCatching {
            val cacheKey = buildCacheKey(latitude, longitude)
            fetchAndSaveWeather(latitude, longitude, cacheKey, isFavorite = true)
        }
    }

    override fun getFavorites(): Flow<List<WeatherModel>> {
        return localDataSource.getFavorites()
            .map { entities -> entities.map { it.toWeatherModel() } }
    }


    private suspend fun fetchAndSaveWeather(
        latitude: Double,
        longitude: Double,
        cacheKey: String,
        isFavorite: Boolean = false
    ): WeatherModel {
        val model = remoteDataSource.getCurrentWeather(latitude, longitude).toWeatherModel()
        localDataSource.saveWeather(model.toWeatherEntity(cacheKey).copy(isFavorite = isFavorite))
        return model
    }

    private suspend fun fetchAndSaveForecast(
        latitude: Double,
        longitude: Double,
        cacheKey: String
    ): ForecastModel {
        val model = remoteDataSource.getFiveDayForecast(latitude, longitude).toForecastModel()
        localDataSource.saveForecast(model.toForecastEntity(cacheKey))
        return model
    }

    private fun isCacheExpired(lastUpdated: Long) =
        System.currentTimeMillis() - lastUpdated > CACHE_EXPIRY_MS

    private suspend fun buildCacheKey(latitude: Double, longitude: Double): String {
        val lang = appPreferences.language.first().code
        return "${"%.2f".format(latitude)},${"%.2f".format(longitude)},$lang"
    }
}