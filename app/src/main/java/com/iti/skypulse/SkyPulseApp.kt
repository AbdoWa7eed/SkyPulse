package com.iti.skypulse

import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import com.iti.skypulse.di.ServiceLocator


class SkyPulseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
        MapsInitializer.initialize(this)
    }
}