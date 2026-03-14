package com.iti.skypulse.ui.alarms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AlarmsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AlarmsViewModel() as T
    }
}