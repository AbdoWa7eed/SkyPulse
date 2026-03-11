package com.iti.skypulse

import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import com.iti.skypulse.di.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SkyPulseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
        MapsInitializer.initialize(this)
    }
}