package com.iti.skypulse.data.repository.weather

import com.iti.skypulse.core.error.AppException
import com.iti.skypulse.core.network.ConnectivityHelper
import com.iti.skypulse.data.local.datasource.WeatherLocalDataSource
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.data.model.weather.FavoriteWeather
import com.iti.skypulse.data.model.weather.ForecastModel
import com.iti.skypulse.data.model.location.GeoPlace
import com.iti.skypulse.data.model.location.mapper.toGeoPlace
import com.iti.skypulse.data.model.weather.WeatherModel
import com.iti.skypulse.data.model.weather.buildCacheKey
import com.iti.skypulse.data.model.weather.mapper.toFavoriteModel
import com.iti.skypulse.data.model.weather.mapper.toForecastEntity
import com.iti.skypulse.data.model.weather.mapper.toForecastModel
import com.iti.skypulse.data.model.weather.mapper.toWeatherEntity
import com.iti.skypulse.data.model.weather.mapper.toWeatherModel
import com.iti.skypulse.data.remote.datasource.WeatherRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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
            val lang = getLang()
            val cacheKey = buildCacheKey(latitude, longitude)
            val cached = localDataSource.getWeather(cacheKey, lang)
            when {
                cached != null && !isCacheExpired(cached.lastUpdated) -> cached.toWeatherModel()
                connectivityHelper.isOnline() -> {
                    val isFav = localDataSource.isFavorite(cacheKey)
                    fetchAndSaveWeather(latitude, longitude, cacheKey, lang, isFav)
                }
                else -> cached?.toWeatherModel() ?: throw AppException.NoCacheException()
            }
        }
    }

    override suspend fun getFiveDayForecast(latitude: Double, longitude: Double): Result<ForecastModel> {
        return runCatching {
            val lang = getLang()
            val cacheKey = buildCacheKey(latitude, longitude)
            val cached = localDataSource.getForecast(cacheKey, lang)
            when {
                cached != null && !isCacheExpired(cached.lastUpdated) -> cached.toForecastModel()
                connectivityHelper.isOnline() -> fetchAndSaveForecast(latitude, longitude, cacheKey, lang)
                else -> cached?.toForecastModel() ?: throw AppException.NoCacheException()
            }
        }
    }

    override suspend fun searchPlaces(query: String): Result<List<GeoPlace>> {
        return runCatching {
            val langCode = getLang()
            remoteDataSource.searchPlaces(query).map { dto -> dto.toGeoPlace(langCode) }
        }
    }

    override suspend fun addFavorite(latitude: Double, longitude: Double): Result<Unit> {
        return runCatching {
            val lang = getLang()
            val cacheKey = buildCacheKey(latitude, longitude)
            val cachedForecast = localDataSource.getForecast(cacheKey, lang)
            if (cachedForecast == null || isCacheExpired(cachedForecast.lastUpdated)) {
                fetchAndSaveForecast(latitude, longitude, cacheKey, lang)
            }
            val cachedWeather = localDataSource.getWeather(cacheKey, lang)
            if (cachedWeather == null || isCacheExpired(cachedWeather.lastUpdated)) {
                fetchAndSaveWeather(latitude, longitude, cacheKey, lang, isFavorite = true)
            } else {
                localDataSource.markAsFavorite(cacheKey)
            }
        }
    }

    override suspend fun removeFavorite(latitude: Double, longitude: Double) {
        val cacheKey = buildCacheKey(latitude, longitude)
        localDataSource.unmarkAsFavorite(cacheKey)
    }

    override suspend fun refreshFavorite(latitude: Double, longitude: Double) {
        if (!connectivityHelper.isOnline()) return
        runCatching {
            val lang = getLang()
            val cacheKey = buildCacheKey(latitude, longitude)
            fetchAndSaveWeather(latitude, longitude, cacheKey, lang, isFavorite = true)
            fetchAndSaveForecast(latitude, longitude, cacheKey, lang)
        }
    }
    override fun getFavorites(): Flow<List<FavoriteWeather>> {
        return flow {
            val lang = getLang()
            syncFavoritesForLanguage(lang)
            emitAll(
                localDataSource.getFavorites(lang)
                    .map { entities -> entities.map { it.toFavoriteModel() } }
            )
        }
    }

    private suspend fun syncFavoritesForLanguage(lang: String) {
        if (!connectivityHelper.isOnline()) return
        val allFavorites = localDataSource.getAllFavoriteLocations()
        allFavorites.forEach { (lat, lng) ->
            val key = buildCacheKey(lat, lng)
            val hasWeather = localDataSource.getWeather(key, lang) != null
            val hasForecast = localDataSource.getForecast(key, lang) != null
            if (!hasWeather || !hasForecast) {
                runCatching {
                    if (!hasWeather) fetchAndSaveWeather(lat, lng, key, lang, isFavorite = true)
                    if (!hasForecast) fetchAndSaveForecast(lat, lng, key, lang)
                }
            }
        }
    }


    private suspend fun fetchAndSaveWeather(
        latitude: Double,
        longitude: Double,
        cacheKey: String,
        lang: String,
        isFavorite: Boolean = false
    ): WeatherModel {
        val model = remoteDataSource.getCurrentWeather(latitude, longitude).toWeatherModel()
        localDataSource.saveWeather(model.toWeatherEntity(cacheKey, lang).copy(isFavorite = isFavorite))
        return model
    }

    private suspend fun fetchAndSaveForecast(
        latitude: Double,
        longitude: Double,
        cacheKey: String,
        lang: String
    ): ForecastModel {
        val model = remoteDataSource.getFiveDayForecast(latitude, longitude).toForecastModel()
        localDataSource.saveForecast(model.toForecastEntity(cacheKey, lang))
        return model
    }

    private fun isCacheExpired(lastUpdated: Long) =
        System.currentTimeMillis() - lastUpdated > CACHE_EXPIRY_MS

    private suspend fun getLang(): String = appPreferences.language.first().code
}