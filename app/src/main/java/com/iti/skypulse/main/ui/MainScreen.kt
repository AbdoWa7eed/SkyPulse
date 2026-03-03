package com.iti.skypulse.main.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iti.skypulse.alarms.AlarmsScreen
import com.iti.skypulse.favorites.FavoriteLocationsScreen
import com.iti.skypulse.forecast.ForecastScreen
import com.iti.skypulse.home.HomeScreen
import com.iti.skypulse.navigation.NavRoutes
import com.iti.skypulse.settings.SettingsScreen

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    BackHandler(enabled = true) {
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
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
                BackHandler(enabled = true) {}
                SettingsScreen()
            }
        }
    }
}