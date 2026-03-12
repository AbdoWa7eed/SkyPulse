package com.iti.skypulse.data.model

fun buildCacheKey(latitude: Double, longitude: Double, lang: String): String {
    return "${"%.2f".format(latitude)},${"%.2f".format(longitude)},$lang"
}