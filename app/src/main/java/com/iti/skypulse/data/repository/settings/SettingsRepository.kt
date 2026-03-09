package com.iti.skypulse.data.repository.settings

import com.iti.skypulse.core.utils.Language
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.data.model.SavedLocation
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val isOnboardingCompleted: Flow<Boolean>
    val language: Flow<Language>
    val tempUnit: Flow<TempUnit>
    val windUnit: Flow<WindUnit>
    val pressureUnit: Flow<PressureUnit>
    val savedLocation: Flow<SavedLocation?>
    suspend fun setLanguage(language: Language)
    suspend fun setTempUnit(unit: TempUnit)
    suspend fun setWindUnit(unit: WindUnit)
    suspend fun setPressureUnit(unit: PressureUnit)
    suspend fun saveLocation(location: SavedLocation)

    suspend fun setOnboardingCompleted()


}