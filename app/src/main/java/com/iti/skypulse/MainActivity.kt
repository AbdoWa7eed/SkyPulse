package com.iti.skypulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.iti.skypulse.ui.navigation.AppNavigation
import com.iti.skypulse.ui.preferences.LocalPreferencesViewModel
import com.iti.skypulse.ui.preferences.PreferencesViewModel
import com.iti.skypulse.ui.preferences.PreferencesViewModelFactory
import com.iti.skypulse.ui.theme.SkyPulseTheme

class MainActivity : ComponentActivity() {
    private val preferencesViewModel: PreferencesViewModel by viewModels {
        PreferencesViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            CompositionLocalProvider(
                LocalPreferencesViewModel provides preferencesViewModel
            ) {
                SkyPulseTheme {
                    AppNavigation()
                }
            }
        }
    }
}
