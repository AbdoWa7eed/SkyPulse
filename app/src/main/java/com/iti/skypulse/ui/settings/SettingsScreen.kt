package com.iti.skypulse.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.ThemeMode
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.ui.components.PrimaryAppBar
import com.iti.skypulse.ui.settings.components.*
import com.iti.skypulse.ui.theme.SkyPulseTheme

@Composable
fun SettingsScreen() {
    var selectedTempUnit by remember { mutableStateOf(TempUnit.CELSIUS) }
    var selectedWindUnit by remember { mutableStateOf(WindUnit.METERS_PER_SECOND) }
    var selectedPressureUnit by remember { mutableStateOf(PressureUnit.HPA) }
    var selectedThemeMode by remember { mutableStateOf(ThemeMode.SYSTEM) }
    var selectedLanguage by remember { mutableStateOf("en") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())

    ) {
        PrimaryAppBar(title = stringResource(R.string.settings))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LanguageSection(
                currentLanguageCode = selectedLanguage,
                onLanguageChange = { selectedLanguage = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            MeasurementUnitsSection(
                tempUnit = selectedTempUnit,
                windUnit = selectedWindUnit,
                pressureUnit = selectedPressureUnit,
                onTempUnitChange = { selectedTempUnit = it },
                onWindUnitChange = { selectedWindUnit = it },
                onPressureUnitChange = { selectedPressureUnit = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            AppearanceSection(
                currentMode = selectedThemeMode,
                onModeChange = { selectedThemeMode = it }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        SettingsVersion()
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SkyPulseTheme {
        SettingsScreen()
    }
}