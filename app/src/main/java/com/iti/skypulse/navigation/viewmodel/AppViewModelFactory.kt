package com.iti.skypulse.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.di.ServiceLocator

class AppViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AppViewModel(ServiceLocator.appPreferences) as T
    }
}