package com.iti.skypulse.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.skypulse.di.ServiceLocator

class SettingsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SettingsViewModel(ServiceLocator.settingsRepository) as T
    }
}
