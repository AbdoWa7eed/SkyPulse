package com.iti.skypulse.ui.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.iti.skypulse.R
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.ui.components.PrimaryCard

@Composable
fun MeasurementUnitsSection(
    tempUnit: TempUnit,
    windUnit: WindUnit,
    pressureUnit: PressureUnit,
    onTempUnitChange: (TempUnit) -> Unit,
    onWindUnitChange: (WindUnit) -> Unit,
    onPressureUnitChange: (PressureUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SettingsSectionHeader(title = stringResource(R.string.measurement_units))
        PrimaryCard {
            Column {
                SettingsRow(
                    icon = Icons.Default.Thermostat,
                    label = stringResource(R.string.temperature)
                ) {
                    SegmentedControl(
                        options = listOf(
                            stringResource(R.string.unit_celsius),
                            stringResource(R.string.unit_fahrenheit),
                            stringResource(R.string.unit_kelvin)
                        ),
                        selectedIndex = tempUnit.ordinal,
                        onOptionSelected = { onTempUnitChange(TempUnit.entries[it]) }
                    )
                }
                SettingsDivider()
                SettingsRow(
                    icon = Icons.Default.Air,
                    label = stringResource(R.string.wind_speed)
                ) {
                    SegmentedControl(
                        options = listOf(
                            stringResource(R.string.unit_ms),
                            stringResource(R.string.unit_kmh),
                            stringResource(R.string.unit_mph)
                        ),
                        selectedIndex = windUnit.ordinal,
                        onOptionSelected = { onWindUnitChange(WindUnit.entries[it]) }
                    )
                }
                SettingsDivider()
                SettingsRow(
                    icon = Icons.Rounded.Speed,
                    label = stringResource(R.string.pressure)
                ) {
                    SegmentedControl(
                        options = listOf(
                            stringResource(R.string.unit_hpa),
                            stringResource(R.string.unit_mbar),
                            stringResource(R.string.unit_mmhg)
                        ),
                        selectedIndex = pressureUnit.ordinal,
                        onOptionSelected = { onPressureUnitChange(PressureUnit.entries[it]) }
                    )
                }
            }
        }
    }
}