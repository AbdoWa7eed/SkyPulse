package com.iti.skypulse.ui.navigation

import androidx.annotation.StringRes
import com.iti.skypulse.R

enum class MapSource(@param:StringRes val titleRes: Int) {
    ONBOARDING(R.string.choose_location),
    UPDATE_LOCATION(R.string.update_location),
    ADD_FAVORITE(R.string.add_favorite)
}