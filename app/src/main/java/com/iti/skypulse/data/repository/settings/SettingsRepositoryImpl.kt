package com.iti.skypulse.data.repository.settings

import com.iti.skypulse.core.utils.Language
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.ThemeMode
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.data.model.SavedLocation
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(private val preferences: AppPreferences) : SettingsRepository {

    override val isOnboardingCompleted: Flow<Boolean> = preferences.isOnboardingCompleted

    override val language: Flow<Language> = preferences.language
    override val tempUnit: Flow<TempUnit> = preferences.tempUnit
    override val windUnit: Flow<WindUnit> = preferences.windUnit
    override val pressureUnit: Flow<PressureUnit> = preferences.pressureUnit
    override val savedLocation: Flow<SavedLocation?> = preferences.savedLocation
    override val themeMode: Flow<ThemeMode> = preferences.themeMode


    override suspend fun setOnboardingCompleted() {
        preferences.setOnboardingCompleted(true)
    }

    override suspend fun setLanguage(language: Language) {
        preferences.saveLanguage(language)
    }

    override suspend fun setTempUnit(unit: TempUnit) {
        preferences.saveTempUnit(unit)
    }

    override suspend fun setWindUnit(unit: WindUnit) {
        preferences.saveWindUnit(unit)
    }

    override suspend fun setPressureUnit(unit: PressureUnit) {
        preferences.savePressureUnit(unit)
    }

    override suspend fun saveLocation(location: SavedLocation) {
        preferences.saveLocation(location)
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        preferences.saveThemeMode(mode)
    }
}