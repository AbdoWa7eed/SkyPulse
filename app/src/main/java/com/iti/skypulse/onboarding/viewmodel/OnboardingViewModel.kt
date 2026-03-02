package com.iti.skypulse.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.data.local.prefs.AppPreferences
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class OnboardingViewModel(
    private val appPreferences: AppPreferences
) : ViewModel() {
    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<Unit>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onNext() {
        if (_currentPage.value < 2) {
            _currentPage.value += 1
        } else {
            finishOnboarding()
        }
    }

    fun finishOnboarding() {
        viewModelScope.launch {
            appPreferences.setOnboardingCompleted(true)
            _navigationEvent.emit(Unit)
        }
    }

    fun updateCurrentPage(page: Int) {
        _currentPage.value = page
    }
}