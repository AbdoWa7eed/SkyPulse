package com.iti.skypulse.core.error

import androidx.annotation.StringRes
import com.iti.skypulse.R

sealed class AppException : Exception() {
    class NoInternetException : AppException()
    class NoCacheException : AppException()
    class ApiException(val code: Int, override val message: String) : AppException()
    class LocationPermissionException : AppException()
    class LocationDisabledException : AppException()

    class AlertExpiredException : AppException()

}

@StringRes
fun Throwable?.toMessageRes(): Int {
    return when (this) {
        is AppException.NoInternetException -> R.string.error_no_internet
        is AppException.NoCacheException    -> R.string.error_no_cache
        is AppException.LocationPermissionException -> R.string.permission_required_message
        is AppException.LocationDisabledException   -> R.string.location_disabled_message
        is AppException.AlertExpiredException -> R.string.error_past_time
        else                                -> R.string.error_generic
    }
}