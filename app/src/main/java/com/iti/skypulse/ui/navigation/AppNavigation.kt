package com.iti.skypulse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.iti.skypulse.ui.location.LocationPickerScreen
import com.iti.skypulse.ui.main.MainScreen
import com.iti.skypulse.ui.map.MapScreen
import com.iti.skypulse.ui.navigation.animation.slideInRight
import com.iti.skypulse.ui.navigation.animation.slideOutLeft
import com.iti.skypulse.ui.onboarding.OnboardingScreen
import com.iti.skypulse.ui.splash.AnimatedSplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val startupViewModel: StartupViewModel = viewModel(factory = StartupViewModelFactory())
    val postSplashDestination by startupViewModel.postSplashDestination.collectAsState()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.SplashGraph
    ) {

        composable<NavRoutes.SplashGraph>(
            exitTransition = { slideOutLeft() }
        ) {
            AnimatedSplashScreen(
                onFinished = {
                    val destination = postSplashDestination ?: return@AnimatedSplashScreen
                    navController.navigate(destination) {
                        popUpTo(NavRoutes.SplashGraph) { inclusive = true }
                    }
                }
            )
        }

        navigation<NavRoutes.LocationGraph>(
            startDestination = NavRoutes.OnboardingRoute
        ) {
            composable<NavRoutes.OnboardingRoute>(
                enterTransition = { slideInRight() },
                exitTransition = { slideOutLeft() }
            ) {
                OnboardingScreen(
                    onFinished = { navController.navigate(NavRoutes.LocationPickerRoute) }
                )
            }

            composable<NavRoutes.LocationPickerRoute>(
                enterTransition = { slideInRight() },
                exitTransition = { slideOutLeft() }
            ) {
                LocationPickerScreen(
                    onLocationSet = {
                        navController.navigate(NavRoutes.MainGraph) {
                            popUpTo(NavRoutes.LocationGraph) { inclusive = true }
                        }
                    },
                    onMapSelected = {
                        navController
                            .navigate(NavRoutes.MapPickerRoute(MapSource.ONBOARDING))
                    }
                )
            }

            composable<NavRoutes.MapPickerRoute>(
                enterTransition = { slideInRight() },
                exitTransition = { slideOutLeft() }
            ) { backStackEntry ->
                val source = backStackEntry.toRoute<NavRoutes.MapPickerRoute>().source
                MapScreen(
                    source = source,
                    onBack = { navController.popBackStack() },
                    onNavigateToMain = {
                        navController.navigate(NavRoutes.MainGraph) {
                            popUpTo(NavRoutes.LocationGraph) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable<NavRoutes.MainGraph>(
            enterTransition = { slideInRight() }
        ) {
            MainScreen()
        }
    }
}