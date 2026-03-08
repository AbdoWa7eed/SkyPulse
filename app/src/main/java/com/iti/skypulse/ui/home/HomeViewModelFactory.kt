package com.iti.skypulse.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.skypulse.di.ServiceLocator

class HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(
            weatherRepository = ServiceLocator.weatherRepository,
            appPreferences = ServiceLocator.appPreferences,
            locationHelper = ServiceLocator.locationHelper
        ) as T
    }
}