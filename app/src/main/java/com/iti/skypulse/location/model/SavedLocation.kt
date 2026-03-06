package com.iti.skypulse.location.model

data class SavedLocation(
    val lat: Double,
    val lng: Double,
    val provider: LocationProvider,
    val address: String? = null
)