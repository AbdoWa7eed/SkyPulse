package com.iti.skypulse

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
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

    private lateinit var appliedLanguage: Language
    private lateinit var appliedTheme: ThemeMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appliedLanguage = runBlocking {
            ServiceLocator.settingsRepository.language.first()
        }

        appliedTheme = runBlocking {
            ServiceLocator.settingsRepository.themeMode.first()
        }

        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            val themeMode by ServiceLocator.settingsRepository.themeMode
                .collectAsStateWithLifecycle(appliedTheme)

            val language by ServiceLocator.settingsRepository.language
                .collectAsStateWithLifecycle(appliedLanguage)

            LaunchedEffect(language) {
                if (language != appliedLanguage) restartWithLanguage()
            }

            SkyPulseTheme(themeMode = themeMode) {
                AppNavigation()
            }
        }
    }

    override fun attachBaseContext(base: Context) {
        val langCode = LanguagePreference.getLanguageCode(base)
        val locale = Locale.forLanguageTag(langCode)
        Locale.setDefault(locale)
        val config = Configuration(base.resources.configuration)
        config.setLocale(locale)
        runBlocking { ServiceLocator.reinit() }
        super.attachBaseContext(base.createConfigurationContext(config))
    }

    private fun restartWithLanguage() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)!!
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(
                OVERRIDE_TRANSITION_OPEN,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

}