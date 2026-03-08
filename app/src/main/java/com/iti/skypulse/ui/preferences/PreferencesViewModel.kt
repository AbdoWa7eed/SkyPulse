package com.iti.skypulse.ui.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.data.local.prefs.AppPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PreferencesViewModel(
    private val appPreferences: AppPreferences
) : ViewModel() {

    val tempUnit: StateFlow<TempUnit> = appPreferences.tempUnit
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TempUnit.CELSIUS
        )

    val windUnit: StateFlow<WindUnit> = appPreferences.windUnit
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WindUnit.METERS_PER_SECOND
        )

    val pressureUnit: StateFlow<PressureUnit> = appPreferences.pressureUnit
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PressureUnit.HPA
        )
}