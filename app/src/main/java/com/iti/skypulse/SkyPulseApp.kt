package com.iti.skypulse

import android.app.Application
import com.iti.skypulse.di.ServiceLocator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale

class SkyPulseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
        applyLanguage()
    }

    private fun applyLanguage() {
        val langCode = runBlocking { ServiceLocator.appPreferences.language.first() }
        val locale = Locale.forLanguageTag(langCode)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        createConfigurationContext(config)
    }
}