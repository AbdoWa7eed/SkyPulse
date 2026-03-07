package com.iti.skypulse.core.error

sealed class AppException : Exception() {
    class NoInternetException : AppException()
    class NoCacheException : AppException()
    class ApiException(val code: Int, override val message: String) : AppException()
}