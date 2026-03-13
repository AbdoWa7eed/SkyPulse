package com.iti.skypulse.data.model

import java.util.Locale

fun buildCacheKey(latitude: Double, longitude: Double): String {
    return "%.2f,%.2f".format(Locale.US, latitude, longitude)
}