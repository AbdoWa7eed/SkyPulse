package com.iti.skypulse.ui.theme

import androidx.compose.runtime.compositionLocalOf
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.WindUnit

data class AppUnits(
    val tempUnit: TempUnit,
    val windUnit: WindUnit,
    val pressureUnit: PressureUnit
)

val LocalAppUnits = compositionLocalOf {
    AppUnits(TempUnit.CELSIUS, WindUnit.METERS_PER_SECOND, PressureUnit.HPA)
}