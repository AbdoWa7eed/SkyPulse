package com.iti.skypulse.data.model

data class SavedLocation(
    val lat: Double,
    val lng: Double,
    val provider: LocationProvider,
    val address: String?
)