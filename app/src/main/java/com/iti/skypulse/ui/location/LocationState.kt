package com.iti.skypulse.ui.location

import com.iti.skypulse.data.model.location.SavedLocation

sealed class LocationState {
    data object NotSet : LocationState()
    data object Loading : LocationState()
    data class Set(val location: SavedLocation) : LocationState()
}