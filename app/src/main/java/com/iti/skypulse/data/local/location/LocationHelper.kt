package com.iti.skypulse.data.local.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.iti.skypulse.core.error.AppException
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.data.model.LocationProvider
import com.iti.skypulse.data.model.SavedLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.Locale
import kotlin.coroutines.resume

class LocationHelper(
    private val context: Context,
    private val appPreferences: AppPreferences
) {

    private val fusedLocationProvider: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val _refreshTrigger = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    suspend fun refresh() = _refreshTrigger.emit(Unit)

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentLocation: Flow<SavedLocation?> = combine(
        appPreferences.savedLocation,
        _refreshTrigger
    ) { saved, _ -> saved }
        .flatMapLatest { saved ->
            if (saved?.provider == LocationProvider.GPS) {
                flow {
                    val gpsLocation = getLocation().getOrNull()?.let { loc ->
                        val updated = SavedLocation(
                            lat = loc.latitude,
                            lng = loc.longitude,
                            provider = LocationProvider.GPS,
                            address = getAddressFromLocation(loc.latitude, loc.longitude)
                        )
                        appPreferences.saveLocation(updated)
                        updated
                    }
                    emit(gpsLocation ?: saved)
                }
            } else {
                flowOf(saved)
            }
        }
        .distinctUntilChanged()

    @SuppressLint("MissingPermission")
    suspend fun getLocation(): Result<Location?> = runCatching {
        when {
            !hasLocationPermission() -> throw AppException.LocationPermissionException()
            !isLocationEnabled()     -> throw AppException.LocationDisabledException()
            else -> {
                val cancellationToken = CancellationTokenSource()
                fusedLocationProvider.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    cancellationToken.token
                ).await()
            }
        }
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
        } catch (_: Exception) {
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

    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun isGpsAvailable(): Boolean = hasLocationPermission() && isLocationEnabled()
}