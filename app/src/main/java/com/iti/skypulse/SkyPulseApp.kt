package com.iti.skypulse

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.data.local.prefs.LanguagePreference
import com.iti.skypulse.di.ServiceLocator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale

class SkyPulseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
    }
}