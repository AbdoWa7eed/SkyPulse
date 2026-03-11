package com.iti.skypulse.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.data.local.location.LocationHelper
import com.iti.skypulse.di.ServiceLocator
import kotlinx.coroutines.launch

class MainViewModel(
    private val locationHelper: LocationHelper
) : ViewModel() {

    fun onGpsStateChanged() {
        viewModelScope.launch { locationHelper.refresh() }
    }
}

