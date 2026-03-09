package com.iti.skypulse

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iti.skypulse.core.utils.Language
import com.iti.skypulse.core.utils.ThemeMode
import com.iti.skypulse.data.local.prefs.LanguagePreference
import com.iti.skypulse.di.ServiceLocator
import com.iti.skypulse.ui.navigation.AppNavigation
import com.iti.skypulse.ui.theme.SkyPulseTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var appliedTheme: ThemeMode


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        appliedTheme = runBlocking {
            ServiceLocator.settingsRepository.themeMode.first()
        }

        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val themeMode by ServiceLocator.settingsRepository.themeMode
                .collectAsStateWithLifecycle(appliedTheme)

            SkyPulseTheme(themeMode = themeMode) {
                AppNavigation()
            }
        }
    }


}