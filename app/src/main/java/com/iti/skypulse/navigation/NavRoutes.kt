package com.iti.skypulse.navigation

import kotlinx.serialization.Serializable

sealed class NavRoutes {

    @Serializable
    data object SplashGraph : NavRoutes()

    @Serializable
    data object LocationGraph : NavRoutes()

    @Serializable
    data object MainGraph : NavRoutes()

    @Serializable
    data object OnboardingRoute : NavRoutes()

    @Serializable
    data object LocationPickerRoute : NavRoutes()

    @Serializable
    data object MapPickerRoute : NavRoutes()

    @Serializable
    data object HomeRoute : NavRoutes()

    @Serializable
    data object ForecastRoute : NavRoutes()

    @Serializable
    data object FavoriteLocationsRoute : NavRoutes()

    @Serializable
    data object AlarmsRoute : NavRoutes()

    @Serializable
    data object SettingsRoute : NavRoutes()
}