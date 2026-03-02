package com.iti.skypulse.di

import android.app.Application
import com.iti.skypulse.data.local.prefs.AppPreferences

object ServiceLocator {
    private lateinit var appContext: Application

    fun init(application: Application) {
        appContext = application
    }

    val appPreferences: AppPreferences by lazy {
        AppPreferences(appContext)
    }
}