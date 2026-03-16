package com.iti.skypulse.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.core.extensions.toStateFlow
import com.iti.skypulse.core.utils.Language
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.ThemeMode
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.data.local.location.LocationHelper
import com.iti.skypulse.data.model.location.LocationProvider
import com.iti.skypulse.data.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class SettingsEvent {
    data object ShowGpsUnavailableWarning : SettingsEvent()
}

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    val language = settingsRepository.language
        .toStateFlow(viewModelScope, Language.ENGLISH)

    val savedLocation = settingsRepository.savedLocation
        .toStateFlow(viewModelScope, null)

    val themeMode = settingsRepository.themeMode
        .toStateFlow(viewModelScope, ThemeMode.SYSTEM)

    private val _events = MutableSharedFlow<SettingsEvent>()
    val events = _events.asSharedFlow()

    val isGpsAvailable: Boolean
        get() = locationHelper.isGpsAvailable()

    fun setTempUnit(unit: TempUnit) =
        viewModelScope.launch { settingsRepository.setTempUnit(unit) }

    fun setWindUnit(unit: WindUnit) =
        viewModelScope.launch { settingsRepository.setWindUnit(unit) }

    fun setPressureUnit(unit: PressureUnit) =
        viewModelScope.launch { settingsRepository.setPressureUnit(unit) }

    fun setLanguage(language: Language) =
        viewModelScope.launch { settingsRepository.setLanguage(language) }

    fun setLocationProvider(provider: LocationProvider) {
        viewModelScope.launch {
            val current = savedLocation.value ?: return@launch
            settingsRepository.saveLocation(current.copy(provider = provider))
            if (provider == LocationProvider.GPS && !isGpsAvailable) {
                _events.emit(SettingsEvent.ShowGpsUnavailableWarning)
            }
        }
    }

    fun setThemeMode(mode: ThemeMode) =
        viewModelScope.launch { settingsRepository.setThemeMode(mode) }
}