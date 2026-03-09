package com.iti.skypulse.data.local.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.iti.skypulse.core.utils.Language
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.data.model.LocationProvider
import com.iti.skypulse.data.model.SavedLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class AppPreferences(private val context: Context) {

    companion object {
        private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
        private val LOCATION_LAT_KEY = doublePreferencesKey("location_lat")
        private val LOCATION_LNG_KEY = doublePreferencesKey("location_lng")
        private val LOCATION_PROVIDER_KEY = stringPreferencesKey("location_provider")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val TEMP_UNIT_KEY = stringPreferencesKey("temp_unit")
        private val WIND_UNIT_KEY = stringPreferencesKey("wind_unit")
        private val PRESSURE_UNIT_KEY = stringPreferencesKey("pressure_unit")
    }

    val isOnboardingCompleted: Flow<Boolean> = context.dataStore.data
        .map { it[ONBOARDING_COMPLETED_KEY] ?: false }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { it[ONBOARDING_COMPLETED_KEY] = completed }
    }

    suspend fun saveLocation(location: SavedLocation) {
        context.dataStore.edit {
            it[LOCATION_LAT_KEY] = location.lat
            it[LOCATION_LNG_KEY] = location.lng
            it[LOCATION_PROVIDER_KEY] = location.provider.name
        }
    }

    val savedLocation: Flow<SavedLocation?> = context.dataStore.data
        .map { prefs ->
            val lat = prefs[LOCATION_LAT_KEY]
            val lng = prefs[LOCATION_LNG_KEY]
            val provider = prefs[LOCATION_PROVIDER_KEY]?.let {
                runCatching { LocationProvider.valueOf(it) }.getOrNull()
            }
            if (lat != null && lng != null && provider != null) SavedLocation(lat, lng, provider)
            else null
        }

    val language: Flow<Language> = context.dataStore.data
        .map { prefs ->
            Language.fromCode(prefs[LANGUAGE_KEY] ?: Language.ENGLISH.code)
        }

    suspend fun saveLanguage(language: Language) {
        context.dataStore.edit { it[LANGUAGE_KEY] = language.code }
    }

    val tempUnit: Flow<TempUnit> = context.dataStore.data
        .map { prefs ->
            prefs[TEMP_UNIT_KEY]?.let { runCatching { TempUnit.valueOf(it) }.getOrNull() }
                ?: TempUnit.CELSIUS
        }

    suspend fun saveTempUnit(unit: TempUnit) {
        context.dataStore.edit { it[TEMP_UNIT_KEY] = unit.name }
    }

    val windUnit: Flow<WindUnit> = context.dataStore.data
        .map { prefs ->
            prefs[WIND_UNIT_KEY]?.let { runCatching { WindUnit.valueOf(it) }.getOrNull() }
                ?: WindUnit.METERS_PER_SECOND
        }

    suspend fun saveWindUnit(unit: WindUnit) {
        context.dataStore.edit { it[WIND_UNIT_KEY] = unit.name }
    }

    val pressureUnit: Flow<PressureUnit> = context.dataStore.data
        .map { prefs ->
            prefs[PRESSURE_UNIT_KEY]?.let { runCatching { PressureUnit.valueOf(it) }.getOrNull() }
                ?: PressureUnit.HPA
        }

    suspend fun savePressureUnit(unit: PressureUnit) {
        context.dataStore.edit { it[PRESSURE_UNIT_KEY] = unit.name }
    }
}