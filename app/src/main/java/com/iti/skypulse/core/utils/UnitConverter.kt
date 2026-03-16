package com.iti.skypulse.core.utils
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.iti.skypulse.R
data class ConvertedValue(
    val value: String,
    @param:StringRes val unitRes: Int
) {

    val numericValue: Double
        get() = value.toDoubleOrNull() ?: 0.0

    @Composable
    fun display(): String {
        return "$value ${stringResource(unitRes)}"
    }

    @Composable
    fun displayInt(): String {
        val intValue = value.toDoubleOrNull()?.toInt() ?: value
        return "$intValue ${stringResource(unitRes)}"
    }

    fun displayValue(): String {
        return "${value.toDoubleOrNull()?.toInt() ?: value}°"
    }
}

object UnitConverter {

    fun formatTemp(kelvin: Double, unit: TempUnit): ConvertedValue {
        val converted = when (unit) {
            TempUnit.CELSIUS    -> kelvin - 273.15
            TempUnit.FAHRENHEIT -> (kelvin - 273.15) * 9 / 5 + 32
            TempUnit.KELVIN     -> kelvin
        }
        return ConvertedValue(
            value = "%.1f".format(converted),
            unitRes = when (unit) {
                TempUnit.CELSIUS    -> R.string.unit_celsius
                TempUnit.FAHRENHEIT -> R.string.unit_fahrenheit
                TempUnit.KELVIN     -> R.string.unit_kelvin
            }
        )
    }

    fun formatWind(metersPerSecond: Double, unit: WindUnit): ConvertedValue {
        val converted = when (unit) {
            WindUnit.METERS_PER_SECOND   -> metersPerSecond
            WindUnit.KILOMETERS_PER_HOUR -> metersPerSecond * 3.6
            WindUnit.MILES_PER_HOUR      -> metersPerSecond * 2.237
        }
        return ConvertedValue(
            value = "%.1f".format(converted),
            unitRes = when (unit) {
                WindUnit.METERS_PER_SECOND   -> R.string.unit_ms
                WindUnit.KILOMETERS_PER_HOUR -> R.string.unit_kmh
                WindUnit.MILES_PER_HOUR      -> R.string.unit_mph
            }
        )
    }

    fun formatPressure(hpa: Int, unit: PressureUnit): ConvertedValue {
        val converted = when (unit) {
            PressureUnit.HPA  -> hpa.toDouble()
            PressureUnit.MBAR -> hpa.toDouble()
            PressureUnit.MMHG -> hpa * 0.750062
        }
        return ConvertedValue(
            value = when (unit) {
                PressureUnit.MMHG -> "%.1f".format(converted)
                else              -> "%d".format(converted.toInt())
            },
            unitRes = when (unit) {
                PressureUnit.HPA  -> R.string.unit_hpa
                PressureUnit.MBAR -> R.string.unit_mbar
                PressureUnit.MMHG -> R.string.unit_mmhg
            }
        )
    }
}