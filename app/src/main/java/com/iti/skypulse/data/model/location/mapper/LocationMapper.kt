package com.iti.skypulse.data.model.location.mapper

import com.iti.skypulse.data.model.location.GeoPlace
import com.iti.skypulse.data.remote.dto.GeoPlaceDto

fun GeoPlaceDto.toGeoPlace(langCode: String): GeoPlace {
    return GeoPlace(
        name = localNames?.get(langCode) ?: name,
        latitude = lat,
        longitude = lng,
        country = country,
        state   = state
    )
}
