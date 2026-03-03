package com.iti.skypulse.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.onboarding.model.OnboardingModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class OnboardingViewModel(
    private val appPreferences: AppPreferences
) : ViewModel() {

    val pages = listOf(
        OnboardingModel.FirstPage,
        OnboardingModel.SecondPage,
        OnboardingModel.ThirdPage
    )

    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<Unit>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _showSkip = MutableStateFlow(true)
    val showSkip: StateFlow<Boolean> = _showSkip.asStateFlow()

    fun updateCurrentPage(page: Int) {
        _currentPage.value = page
        if (page == pages.lastIndex) _showSkip.value = false
    }

    fun onNext() {
        if (_currentPage.value < pages.lastIndex) {
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
}