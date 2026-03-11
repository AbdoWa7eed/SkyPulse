package com.iti.skypulse.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GeoPlaceDto(
    @SerializedName("name")        val name: String,
    @SerializedName("local_names") val localNames: Map<String, String>?,
    @SerializedName("lat")         val lat: Double,
    @SerializedName("lon")         val lng: Double,
    @SerializedName("country")     val country: String,
    @SerializedName("state")       val state: String?
)