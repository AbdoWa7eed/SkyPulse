package com.iti.skypulse.ui.alerts

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.core.error.toMessageRes
import com.iti.skypulse.data.model.alert.AlertNotificationType
import com.iti.skypulse.data.model.alert.WeatherAlert
import com.iti.skypulse.data.repository.alerts.WeatherAlertRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class AlertsEvent {
    data class AlertTimeExpired(@param:StringRes val messageRes: Int) : AlertsEvent()
    data object RequestAlarmPermission : AlertsEvent()

}
class AlertsViewModel(
    private val repository: WeatherAlertRepository
) : ViewModel() {

    private val _alerts = MutableStateFlow<List<WeatherAlert>>(emptyList())
    val alerts: StateFlow<List<WeatherAlert>> = _alerts.asStateFlow()

    private val _events = MutableSharedFlow<AlertsEvent>()
    val events = _events.asSharedFlow()

    private val _showAddSheet = MutableStateFlow(false)
    val showAddSheet: StateFlow<Boolean> = _showAddSheet.asStateFlow()

    init {
        observeAlerts()
    }

    private fun observeAlerts() {
        viewModelScope.launch {
            repository.getAlerts().collect { _alerts.value = it }
        }
    }

    fun addAlert(form: AlertFormState) {
        viewModelScope.launch {
            if (form.notificationType == AlertNotificationType.ALARM) {
                _events.emit(AlertsEvent.RequestAlarmPermission)
                return@launch
            }
            saveAlert(form)
        }
    }

    fun saveAlertAfterPermission(form: AlertFormState) {
        viewModelScope.launch { saveAlert(form) }
    }

    private suspend fun saveAlert(form: AlertFormState) {
        repository.addAlert(form.toWeatherAlert())
        _showAddSheet.value = false
    }


    fun deleteAlert(id: String) {
        viewModelScope.launch { repository.deleteAlert(id) }
    }

    fun toggleAlert(id: String, enabled: Boolean) {
        viewModelScope.launch {
            repository.toggleAlert(id, enabled)
                .onFailure {
                    _events
                        .emit(AlertsEvent.AlertTimeExpired(it.toMessageRes()))
                }
        }
    }

    fun openAddSheet() { _showAddSheet.value = true }
    fun closeAddSheet() { _showAddSheet.value = false }
}