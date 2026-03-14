package com.iti.skypulse.ui.alarms

import androidx.lifecycle.ViewModel
import com.iti.skypulse.data.model.WeatherAlert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AlarmsViewModel : ViewModel() {

    private val _alerts = MutableStateFlow<List<WeatherAlert>>(emptyList())
    val alerts: StateFlow<List<WeatherAlert>> = _alerts.asStateFlow()

    private val _showAddSheet = MutableStateFlow(false)
    val showAddSheet: StateFlow<Boolean> = _showAddSheet.asStateFlow()

    fun addAlert(form: AlertFormState) {
        _alerts.value += form.toWeatherAlert()
        _showAddSheet.value = false
    }

    fun deleteAlert(id: String) {
        _alerts.value = _alerts.value.filter { it.id != id }
    }

    fun toggleAlert(id: String, enabled: Boolean) {
        _alerts.value = _alerts.value.map { if (it.id == id) it.copy(isEnabled = enabled) else it }
    }

    fun openAddSheet() { _showAddSheet.value = true }
    fun closeAddSheet() { _showAddSheet.value = false }
}