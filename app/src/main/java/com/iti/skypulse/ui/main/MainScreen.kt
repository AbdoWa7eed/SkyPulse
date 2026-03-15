package com.iti.skypulse.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iti.skypulse.ui.alerts.AlertsScreen
import com.iti.skypulse.ui.common.GpsStateObserver
import com.iti.skypulse.ui.favorites.FavoriteLocationsScreen
import com.iti.skypulse.ui.forecast.ForecastScreen
import com.iti.skypulse.ui.home.HomeScreen
import com.iti.skypulse.ui.main.components.BottomNavigationBar
import com.iti.skypulse.ui.navigation.NavRoutes
import com.iti.skypulse.ui.settings.SettingsScreen
import com.iti.skypulse.ui.theme.AppUnits
import com.iti.skypulse.ui.theme.LocalAppUnits

@Composable
fun MainScreen(
    onUpdateLocation: () -> Unit,
    onAddFavorite: () -> Unit,
    viewModel: MainViewModel = viewModel(factory = MainViewModelFactory())
) {
    val tempUnit by viewModel.tempUnit.collectAsState()
    val windUnit by viewModel.windUnit.collectAsState()
    val pressureUnit by viewModel.pressureUnit.collectAsState()
    val navController = rememberNavController()

    GpsStateObserver(onGpsStateChanged = { viewModel.onGpsStateChanged() })

    CompositionLocalProvider(
        LocalAppUnits provides AppUnits(tempUnit, windUnit, pressureUnit)
    ) {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = NavRoutes.HomeRoute,
                modifier = Modifier.padding(padding)
            ) {
                composable<NavRoutes.HomeRoute> {
                    BackHandler(enabled = true) {}
                    HomeScreen()
                }
                composable<NavRoutes.ForecastRoute> {
                    BackHandler(enabled = true) {}
                    ForecastScreen()
                }
                composable<NavRoutes.FavoriteLocationsRoute> {
                    BackHandler(enabled = true) {}
                    FavoriteLocationsScreen(onAddFavorite)
                }
                composable<NavRoutes.AlertsRoute> {
                    BackHandler(enabled = true) {}
                    AlertsScreen()
                }
                composable<NavRoutes.SettingsRoute> {
                    SettingsScreen(onUpdateLocation = onUpdateLocation)
                }
            }
        }
    }
}