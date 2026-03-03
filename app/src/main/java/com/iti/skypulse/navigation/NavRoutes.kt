package com.iti.skypulse.navigation

import kotlinx.serialization.Serializable

sealed class NavRoutes {

    @Serializable
    object SplashRoute : NavRoutes()

    @Serializable
    object OnboardingRoute : NavRoutes()

    @Serializable
    object MainRoute : NavRoutes()
    @Serializable
    object HomeRoute : NavRoutes()

    @Serializable
    data object ForecastRoute : NavRoutes()

    @Serializable
    data object FavoriteLocationsRoute : NavRoutes()

    @Serializable
    data object AlarmsRoute : NavRoutes()

    @Serializable
    data object SettingsRoute : NavRoutes()
}