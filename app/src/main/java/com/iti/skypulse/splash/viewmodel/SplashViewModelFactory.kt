package com.iti.skypulse.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.skypulse.di.ServiceLocator

class SplashViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel>  create(modelClass: Class<T>): T {
        return SplashViewModel(ServiceLocator.appPreferences) as T
    }
}