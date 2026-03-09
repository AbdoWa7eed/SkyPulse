package com.iti.skypulse.di

import android.app.Application
import android.net.ConnectivityManager
import android.content.Context
import com.iti.skypulse.core.network.ConnectivityHelper
import com.iti.skypulse.data.local.datasource.WeatherLocalDataSourceImpl
import com.iti.skypulse.data.local.location.LocationHelper
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.data.local.room.AppDatabase
import com.iti.skypulse.data.remote.api.ApiClient
import com.iti.skypulse.data.remote.api.WeatherApiService
import com.iti.skypulse.data.remote.datasource.WeatherRemoteDataSourceImpl
import com.iti.skypulse.data.repository.WeatherRepositoryImpl
import com.iti.skypulse.data.repository.settings.SettingsRepositoryImpl
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first

object ServiceLocator {
    private lateinit var appContext: Application

    fun init(application: Application) {
        appContext = application
    }

    private val appPreferences: AppPreferences by lazy {
        AppPreferences(appContext)
    }

    val locationHelper: LocationHelper by lazy {
        LocationHelper(appContext, appPreferences)
    }

    val connectivityHelper: ConnectivityHelper by lazy {
        ConnectivityHelper(
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        )
    }

    private val appDatabase: AppDatabase by lazy {
        AppDatabase.getInstance(appContext)
    }

    private val weatherApiService: WeatherApiService by lazy {
        val lang = runBlocking { appPreferences.language.first() }
        ApiClient.init(lang.code)
        ApiClient.getInstance().create(WeatherApiService::class.java)
    }

    private val weatherRemoteDataSource by lazy {
        WeatherRemoteDataSourceImpl(weatherApiService)
    }

    private val weatherLocalDataSource by lazy {
        WeatherLocalDataSourceImpl(appDatabase.weatherDao())
    }

    val weatherRepository by lazy {
        WeatherRepositoryImpl(
            remoteDataSource = weatherRemoteDataSource,
            localDataSource = weatherLocalDataSource,
            connectivityHelper = connectivityHelper
        )
    }

    val settingsRepository by lazy {
        SettingsRepositoryImpl(appPreferences)
    }
}