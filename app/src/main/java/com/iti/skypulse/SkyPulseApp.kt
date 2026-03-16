package com.iti.skypulse

import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import com.iti.skypulse.di.ServiceLocator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class SkyPulseApp : Application() {

    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
        MapsInitializer.initialize(this)
    }
}