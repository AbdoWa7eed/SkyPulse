package com.iti.skypulse.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.skypulse.di.ServiceLocator

class OnboardingViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OnboardingViewModel(ServiceLocator.appPreferences) as T
    }
}