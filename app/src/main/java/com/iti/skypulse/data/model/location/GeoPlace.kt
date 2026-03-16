package com.iti.skypulse.data.model.location

data class GeoPlace(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    val state: String?
) {
    val displayName: String
        get() = listOfNotNull(name, state, country).joinToString(", ")
}