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
    class AlertStartPassedException : AppException()


}

@StringRes
fun Throwable?.toMessageRes(): Int {
    return when (this) {
        is AppException.NoInternetException -> R.string.error_no_internet
        is AppException.NoCacheException    -> R.string.error_no_cache
        is AppException.LocationPermissionException -> R.string.permission_required_message
        is AppException.LocationDisabledException   -> R.string.location_disabled_message
        is AppException.AlertStartPassedException   -> R.string.error_alert_start_passed
        is AppException.AlertExpiredException -> R.string.error_alert_expired
        else                                -> R.string.error_generic
    }
}