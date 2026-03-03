package com.iti.skypulse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iti.skypulse.main.ui.MainScreen
import com.iti.skypulse.onboarding.ui.OnboardingScreen
import com.iti.skypulse.splash.ui.AnimatedSplashScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.SplashRoute
    ) {

        composable<NavRoutes.SplashRoute>(
            exitTransition = { slideOutLeft },
            popExitTransition = { slideOutLeft }
        ) {
            AnimatedSplashScreen { destination ->
                navController.navigate(destination) {
                    popUpTo(NavRoutes.SplashRoute) { inclusive = true }
                }
            }
        }

        composable<NavRoutes.OnboardingRoute>(
            enterTransition = { slideInRight },
            exitTransition = { slideOutLeft }
        ) {
            OnboardingScreen {
                navController.navigate(NavRoutes.MainRoute) {
                    popUpTo(NavRoutes.OnboardingRoute) { inclusive = true }
                }
            }
        }

        composable<NavRoutes.MainRoute>(
            enterTransition = { slideInRight },
            exitTransition = { slideOutLeft }
        ) {
            MainScreen()
        }
    }
}