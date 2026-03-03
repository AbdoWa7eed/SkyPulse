package com.iti.skypulse.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.navigation.NavRoutes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashViewModel(private val appPreferences: AppPreferences) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<NavRoutes>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            val isCompleted = appPreferences.isOnboardingCompleted.first()
            val destination = if (isCompleted) NavRoutes.MainRoute else NavRoutes.OnboardingRoute
            _navigationEvent.emit(destination)
        }
    }
}