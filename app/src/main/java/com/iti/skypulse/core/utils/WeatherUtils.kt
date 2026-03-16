package com.iti.skypulse.core.utils

fun buildWeatherIconUrl(iconCode: String): String =
    "https://openweathermap.org/img/wn/${iconCode}@2x.png"