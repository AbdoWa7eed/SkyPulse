package com.iti.skypulse.data.model.location

import androidx.annotation.StringRes
import com.iti.skypulse.R

enum class LocationProvider(@param:StringRes val labelRes: Int) {
    GPS(R.string.gps),
    MAP(R.string.map)
}
