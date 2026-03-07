package com.iti.skypulse.di

import android.app.Application
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.data.local.location.LocationHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale

object ServiceLocator {
    private lateinit var appContext: Application

    fun init(application: Application) {
        appContext = application
    }

    val appPreferences: AppPreferences by lazy {
        AppPreferences(appContext)
    }

    val locationHelper: LocationHelper by lazy {
        LocationHelper(appContext)
    }
}