package com.iti.skypulse.core.error

import androidx.annotation.StringRes
import com.iti.skypulse.R

sealed class AppException : Exception() {
    class NoInternetException : AppException()
    class NoCacheException : AppException()
    class ApiException(val code: Int, override val message: String) : AppException()
}

@StringRes
fun Throwable?.toMessageRes(): Int {
    return when (this) {
        is AppException.NoInternetException -> R.string.error_no_internet
        is AppException.NoCacheException    -> R.string.error_no_cache
        else                                -> R.string.error_generic
    }
}