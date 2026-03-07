package com.iti.skypulse.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.iti.skypulse.R
import com.iti.skypulse.ui.navigation.NavRoutes

sealed class BottomNavigationItem(
    @param:StringRes val title: Int,
    @param:DrawableRes val icon: Int,
    val route: NavRoutes
) {

    data object Home : BottomNavigationItem(
        title = R.string.home,
        icon = R.drawable.ic_home,
        route = NavRoutes.HomeRoute
    )

    data object Forecast : BottomNavigationItem(
        title = R.string.forecast,
        icon = R.drawable.ic_forecast,
        route = NavRoutes.ForecastRoute
    )

    data object SavedLocations : BottomNavigationItem(
        title = R.string.favorite_locations,
        icon = R.drawable.ic_favorite_locations,
        route = NavRoutes.FavoriteLocationsRoute
    )

    data object Alarms : BottomNavigationItem(
        title = R.string.alarms,
        icon = R.drawable.ic_alarms,
        route = NavRoutes.AlarmsRoute
    )

    data object Settings : BottomNavigationItem(
        title = R.string.settings,
        icon = R.drawable.ic_settings,
        route = NavRoutes.SettingsRoute
    )

    companion object {
        val items = listOf(
            Home,
            Forecast,
            SavedLocations,
            Alarms,
            Settings
        )
    }
}