package com.iti.skypulse.data.remote.api

import com.iti.skypulse.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private const val API_KEY_FIELD = "appid"
    private const val LANGUAGE_FIELD = "lang"

    @Volatile
    private var instance: Retrofit? = null

    private var language: String? = null

    private val queryParamsInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url.newBuilder()
            .addQueryParameter(LANGUAGE_FIELD, language)
            .addQueryParameter(API_KEY_FIELD, BuildConfig.WEATHER_API_KEY)
            .build()
        chain.proceed(chain.request().newBuilder().url(newUrl).build())
    }

    fun init(language: String) {
        synchronized(this) {
            this.language = language
        }
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(queryParamsInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    fun getInstance(): Retrofit {

        return instance ?: synchronized(this) {
            instance ?: run {
                checkNotNull(language) {
                    "ApiClient not initialized. Call ApiClient.init() before using getInstance()"
                }
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .also { instance = it }
            }
        }
    }
}