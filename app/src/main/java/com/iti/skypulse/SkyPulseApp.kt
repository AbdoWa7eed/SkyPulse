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
        val lang = runBlocking { ServiceLocator.settingsRepository.language.first() }
        val locale = Locale.forLanguageTag(lang.code)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        createConfigurationContext(config)
    }
}