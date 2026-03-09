package com.iti.skypulse.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.core.extensions.toStateFlow
import com.iti.skypulse.core.utils.Language
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.ThemeMode
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.data.model.LocationProvider
import com.iti.skypulse.data.repository.settings.SettingsRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val tempUnit = settingsRepository.tempUnit
        .toStateFlow(viewModelScope, TempUnit.CELSIUS)

    val windUnit = settingsRepository.windUnit
        .toStateFlow(viewModelScope, WindUnit.METERS_PER_SECOND)

    val pressureUnit = settingsRepository.pressureUnit
        .toStateFlow(viewModelScope, PressureUnit.HPA)

    val language = settingsRepository.language
        .toStateFlow(viewModelScope, Language.ENGLISH)

    val savedLocation = settingsRepository.savedLocation
        .toStateFlow(viewModelScope, null)

    val themeMode = settingsRepository.themeMode
        .toStateFlow(viewModelScope, ThemeMode.SYSTEM)


    fun setTempUnit(unit: TempUnit) =
        viewModelScope.launch { settingsRepository.setTempUnit(unit) }

    fun setWindUnit(unit: WindUnit) =
        viewModelScope.launch { settingsRepository.setWindUnit(unit) }

    fun setPressureUnit(unit: PressureUnit) =
        viewModelScope.launch { settingsRepository.setPressureUnit(unit) }

    fun setLanguage(language: Language) =
        viewModelScope.launch { settingsRepository.setLanguage(language) }

    fun setLocationProvider(provider: LocationProvider) {
        val current = savedLocation.value ?: return
        val updated = current.copy(provider = provider)
        viewModelScope.launch { settingsRepository.saveLocation(updated) }
    }

    fun setThemeMode(mode: ThemeMode) =
        viewModelScope.launch { settingsRepository.setThemeMode(mode) }
}