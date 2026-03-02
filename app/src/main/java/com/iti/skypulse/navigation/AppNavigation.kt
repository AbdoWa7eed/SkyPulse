package com.iti.skypulse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iti.skypulse.onboarding.OnboardingScreen
import com.iti.skypulse.splash.AnimatedSplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.SplashRoute
    ) {
        composable<NavRoutes.SplashRoute> {
            AnimatedSplashScreen {
                navController.navigate(NavRoutes.OnboardingRoute) {
                    popUpTo(NavRoutes.SplashRoute) { inclusive = true }
                }
            }
        }

        composable<NavRoutes.OnboardingRoute> {
            OnboardingScreen()
        }

        composable<NavRoutes.HomeRoute> {}
    }
}
