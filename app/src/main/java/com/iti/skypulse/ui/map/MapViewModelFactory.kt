package com.iti.skypulse.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.skypulse.di.ServiceLocator
import com.iti.skypulse.ui.navigation.MapSource

class MapViewModelFactory(
    private val source: MapSource
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MapViewModel(
            source = source,
            weatherRepository = ServiceLocator.weatherRepository,
            locationHelper = ServiceLocator.locationHelper,
            settingsRepository = ServiceLocator.settingsRepository
        ) as T
    }
}