package com.iti.skypulse.data.local.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.iti.skypulse.location.model.LocationProvider
import com.iti.skypulse.location.model.SavedLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class AppPreferences(private val context: Context) {

    companion object {
        private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
        private val LOCATION_LAT_KEY = doublePreferencesKey("location_lat")
        private val LOCATION_LNG_KEY = doublePreferencesKey("location_lng")
        private val LOCATION_PROVIDER_KEY = stringPreferencesKey("location_provider")

    }

    val isOnboardingCompleted: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[ONBOARDING_COMPLETED_KEY] ?: false }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[ONBOARDING_COMPLETED_KEY] = completed
        }
    }

    suspend fun saveLocation(savedLocation: SavedLocation) {
        context.dataStore.edit { prefs ->
            prefs[LOCATION_LAT_KEY] = savedLocation.lat
            prefs[LOCATION_LNG_KEY] = savedLocation.lng
            prefs[LOCATION_PROVIDER_KEY] = savedLocation.provider.name
        }
    }

    suspend fun getSavedLocation(): SavedLocation? {
        val prefs = context.dataStore.data.first()
        val lat = prefs[LOCATION_LAT_KEY]
        val lng = prefs[LOCATION_LNG_KEY]
        val provider = prefs[LOCATION_PROVIDER_KEY]
            ?.let { runCatching { LocationProvider.valueOf(it) }.getOrNull() }
        return if (lat != null && lng != null && provider != null) SavedLocation(lat, lng, provider) else null
    }
}