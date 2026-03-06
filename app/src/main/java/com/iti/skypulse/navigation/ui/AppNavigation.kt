package com.iti.skypulse.navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.iti.skypulse.location.ui.LocationPickerScreen
import com.iti.skypulse.main.ui.MainScreen
import com.iti.skypulse.navigation.NavRoutes
import com.iti.skypulse.navigation.viewmodel.AppViewModel
import com.iti.skypulse.navigation.viewmodel.AppViewModelFactory
import com.iti.skypulse.onboarding.ui.OnboardingScreen
import com.iti.skypulse.splash.ui.AnimatedSplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val appViewModel: AppViewModel = viewModel(factory = AppViewModelFactory())
    val postSplashDestination by appViewModel.postSplashDestination.collectAsState()

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
                    onFinished = {
                        navController.navigate(NavRoutes.LocationPickerRoute)
                    }
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
                        navController.navigate(NavRoutes.MapPickerRoute)
                    }
                )
            }

            composable<NavRoutes.MapPickerRoute>(
                enterTransition = { slideInRight() },
                exitTransition = { slideOutLeft() }
            ) {
                // TODO: MapPickerScreen
            }
        }

        composable<NavRoutes.MainGraph>(
            enterTransition = { slideInRight() }
        ) {
            MainScreen()
        }
    }
}