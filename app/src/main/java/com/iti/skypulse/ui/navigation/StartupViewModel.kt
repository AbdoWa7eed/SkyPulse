package com.iti.skypulse.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.data.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StartupViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _postSplashDestination = MutableStateFlow<NavRoutes?>(null)
    val postSplashDestination: StateFlow<NavRoutes?> = _postSplashDestination

    init {
        viewModelScope.launch {
            val onboardingDone = settingsRepository.isOnboardingCompleted.first()
            val locationSet = settingsRepository.savedLocation.first() != null



            _postSplashDestination.value = when {
                !onboardingDone -> NavRoutes.LocationGraph
                !locationSet -> NavRoutes.LocationPickerRoute
                else            -> NavRoutes.MainGraph
            }
        }
    }
}