package com.iti.skypulse.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.skypulse.di.ServiceLocator

class LocationPickerViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return LocationPickerViewModel(ServiceLocator.locationHelper, ServiceLocator.settingsRepository) as T
    }
}