package com.iti.skypulse.core.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun <T> Flow<T>.toStateFlow(
    scope: CoroutineScope,
    initialValue: T,
    started: SharingStarted = SharingStarted.WhileSubscribed(5000)
): StateFlow<T> = this.stateIn(scope, started, initialValue)

fun String.toLocalizedTime(): String {
    return try {
        val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val output = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = input.parse(this)
        output.format(date ?: Date())
    } catch (_: Exception) {
        this
    }
}


fun String.toDayName(): String {
    return try {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
        SimpleDateFormat("EEEE", Locale.getDefault()).format(date ?: Date())
    } catch (_: Exception) { this }
}

fun String.toFormattedDate(): String {
    return try {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
        SimpleDateFormat("MMM dd", Locale.getDefault()).format(date ?: Date())
    } catch (_: Exception) { this }
}