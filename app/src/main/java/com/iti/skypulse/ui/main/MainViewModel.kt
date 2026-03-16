package com.iti.skypulse.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.core.extensions.toStateFlow
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.data.local.location.LocationHelper
import com.iti.skypulse.data.repository.settings.SettingsRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val locationHelper: LocationHelper,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val tempUnit = settingsRepository.tempUnit
        .toStateFlow(viewModelScope, TempUnit.CELSIUS)

    val windUnit = settingsRepository.windUnit
        .toStateFlow(viewModelScope, WindUnit.METERS_PER_SECOND)

    val pressureUnit = settingsRepository.pressureUnit
        .toStateFlow(viewModelScope, PressureUnit.HPA)

    fun onGpsStateChanged() {
        viewModelScope.launch { locationHelper.refresh() }
    }
}