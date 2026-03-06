package com.iti.skypulse.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.location.model.LocationProvider
import com.iti.skypulse.navigation.NavRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AppViewModel(
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _postSplashDestination = MutableStateFlow<NavRoutes?>(null)
    val postSplashDestination: StateFlow<NavRoutes?> = _postSplashDestination

    init {
        viewModelScope.launch {
            val onboardingDone = appPreferences.isOnboardingCompleted.first()
            val locationSet = appPreferences.getSavedLocation() != null



            _postSplashDestination.value = when {
                !onboardingDone -> NavRoutes.LocationGraph
                !locationSet -> NavRoutes.LocationPickerRoute
                else            -> NavRoutes.MainGraph
            }
        }
    }
}