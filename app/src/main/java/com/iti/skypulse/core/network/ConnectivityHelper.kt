package com.iti.skypulse.core.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class ConnectivityHelper(private val connectivityManager: ConnectivityManager) {

    fun isOnline(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}