package com.iti.skypulse.data.repository.settings

import com.iti.skypulse.core.utils.Language
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.ThemeMode
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.data.local.datasource.WeatherLocalDataSource
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.data.model.location.SavedLocation
import com.iti.skypulse.data.model.weather.buildCacheKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class SettingsRepositoryImpl(
    private val appPreferences: AppPreferences,
    private val weatherLocalDataSource: WeatherLocalDataSource
) : SettingsRepository {

    override val isOnboardingCompleted: Flow<Boolean> = appPreferences.isOnboardingCompleted

    override val language: Flow<Language> = appPreferences.language
    override val tempUnit: Flow<TempUnit> = appPreferences.tempUnit
    override val windUnit: Flow<WindUnit> = appPreferences.windUnit
    override val pressureUnit: Flow<PressureUnit> = appPreferences.pressureUnit
    override val savedLocation: Flow<SavedLocation?> = appPreferences.savedLocation
    override val themeMode: Flow<ThemeMode> = appPreferences.themeMode


    override suspend fun setOnboardingCompleted() {
        appPreferences.setOnboardingCompleted(true)
    }

    override suspend fun setLanguage(language: Language) {
        appPreferences.saveLanguage(language)
    }

    override suspend fun setTempUnit(unit: TempUnit) {
        appPreferences.saveTempUnit(unit)
    }

    override suspend fun setWindUnit(unit: WindUnit) {
        appPreferences.saveWindUnit(unit)
    }

    override suspend fun setPressureUnit(unit: PressureUnit) {
        appPreferences.savePressureUnit(unit)
    }

    override suspend fun saveLocation(location: SavedLocation) {
        val oldLocation = appPreferences.savedLocation.first()

        oldLocation?.let {
            val oldCacheKey = buildCacheKey(it.lat, it.lng)
            val isOldFavorite = weatherLocalDataSource.isFavorite(oldCacheKey)

            if (!isOldFavorite) {
                weatherLocalDataSource.deleteWeather(oldCacheKey)
                weatherLocalDataSource.deleteForecast(oldCacheKey)
            }
        }

        appPreferences.saveLocation(location)
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        appPreferences.saveThemeMode(mode)
    }

}