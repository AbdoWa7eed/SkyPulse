package com.iti.skypulse.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iti.skypulse.ui.alarms.AlarmsScreen
import com.iti.skypulse.ui.favorites.FavoriteLocationsScreen
import com.iti.skypulse.ui.forecast.ForecastScreen
import com.iti.skypulse.ui.home.HomeScreen
import com.iti.skypulse.ui.main.components.BottomNavigationBar
import com.iti.skypulse.ui.navigation.NavRoutes
import com.iti.skypulse.ui.settings.SettingsScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController
            )
        }
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
                FavoriteLocationsScreen()
            }
            composable<NavRoutes.AlarmsRoute> {
                BackHandler(enabled = true) {}
                AlarmsScreen()
            }
            composable<NavRoutes.SettingsRoute> {
                SettingsScreen()
            }
        }
    }
}