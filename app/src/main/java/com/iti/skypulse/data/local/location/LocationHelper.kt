package com.iti.skypulse.data.local.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.Locale
import kotlin.coroutines.resume

class LocationHelper(private val context: Context) {

    private val fusedLocationProvider: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getLocation() = runCatching {
        val cancellationToken = CancellationTokenSource()
        fusedLocationProvider.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationToken.token
        ).await()
    }

    suspend fun getAddressFromLocation(lat: Double, lng: Double): String? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                suspendCancellableCoroutine { continuation ->
                    geocoder.getFromLocation(lat, lng, 1) { addresses ->
                        continuation.resume(addresses.firstOrNull()?.let { formatAddress(it) })
                    }
                }
            } else {
                @Suppress("DEPRECATION")
                geocoder.getFromLocation(lat, lng, 1)
                    ?.firstOrNull()?.let { formatAddress(it) }
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun formatAddress(address: Address): String {
        return listOfNotNull(
            address.locality,
            address.adminArea,
            address.countryName
        ).joinToString(", ")
    }
}