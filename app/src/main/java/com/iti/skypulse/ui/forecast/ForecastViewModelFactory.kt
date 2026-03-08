package com.iti.skypulse.ui.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.skypulse.di.ServiceLocator

class ForecastViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ForecastViewModel(
            weatherRepository = ServiceLocator.weatherRepository,
            appPreferences = ServiceLocator.appPreferences
        ) as T
    }
}