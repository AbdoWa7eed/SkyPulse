package com.iti.skypulse.navigation

import kotlinx.serialization.Serializable

sealed class NavRoutes {

    @Serializable
    object SplashRoute : NavRoutes()

    @Serializable
    object OnboardingRoute : NavRoutes()

    @Serializable
    object HomeRoute : NavRoutes()
}