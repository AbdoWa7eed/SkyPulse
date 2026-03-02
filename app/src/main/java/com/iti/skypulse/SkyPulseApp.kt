package com.iti.skypulse

import android.app.Application
import com.iti.skypulse.di.ServiceLocator

class SkyPulseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
    }
}