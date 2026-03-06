package com.iti.skypulse.location.model


sealed class LocationState {
    data object NotSet : LocationState()
    data object Loading : LocationState()
    data class Set(val location: SavedLocation) : LocationState()
}