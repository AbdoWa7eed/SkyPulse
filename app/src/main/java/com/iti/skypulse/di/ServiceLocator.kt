package com.iti.skypulse.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.iti.skypulse.core.network.ConnectivityHelper
import com.iti.skypulse.data.local.datasource.WeatherLocalDataSourceImpl
import com.iti.skypulse.data.local.location.LocationHelper
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.data.local.prefs.LanguagePreference
import com.iti.skypulse.data.local.room.AppDatabase
import com.iti.skypulse.data.remote.api.ApiClient
import com.iti.skypulse.data.remote.api.WeatherApiService
import com.iti.skypulse.data.remote.datasource.WeatherRemoteDataSourceImpl
import com.iti.skypulse.data.repository.WeatherRepositoryImpl
import com.iti.skypulse.data.repository.settings.SettingsRepositoryImpl

object ServiceLocator {
    private lateinit var appContext: Application

    fun init(application: Application) {
        appContext = application
        reinit()
    }

    fun reinit() {
        val langCode = LanguagePreference.getLanguageCode(appContext)
        ApiClient.init(langCode)

        _weatherApiService = ApiClient.getInstance().create(WeatherApiService::class.java)
        _weatherRemoteDataSource = WeatherRemoteDataSourceImpl(_weatherApiService)
        weatherRepository = WeatherRepositoryImpl(
            appPreferences,
            remoteDataSource = _weatherRemoteDataSource,
            localDataSource = weatherLocalDataSource,
            connectivityHelper = connectivityHelper
        )
    }

    private val appPreferences: AppPreferences by lazy { AppPreferences(appContext) }
    private val appDatabase: AppDatabase by lazy { AppDatabase.getInstance(appContext) }
    private val weatherLocalDataSource by lazy { WeatherLocalDataSourceImpl(appDatabase.weatherDao()) }
    val connectivityHelper: ConnectivityHelper by lazy {
        ConnectivityHelper(appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    }
    val locationHelper: LocationHelper by lazy { LocationHelper(appContext, appPreferences) }
    val settingsRepository by lazy { SettingsRepositoryImpl(appPreferences, weatherLocalDataSource) }

    private lateinit var _weatherApiService: WeatherApiService
    private lateinit var _weatherRemoteDataSource: WeatherRemoteDataSourceImpl
    lateinit var weatherRepository: WeatherRepositoryImpl
        private set
}