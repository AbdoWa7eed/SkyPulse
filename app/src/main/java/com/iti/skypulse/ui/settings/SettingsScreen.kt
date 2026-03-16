package com.iti.skypulse.ui.settings

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iti.skypulse.R
import com.iti.skypulse.data.model.location.LocationProvider
import com.iti.skypulse.ui.components.PrimaryAppBar
import com.iti.skypulse.ui.settings.components.*
import com.iti.skypulse.ui.theme.LocalAppUnits

@Composable
fun SettingsScreen(
    onUpdateLocation: () -> Unit,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory())
) {
    val units = LocalAppUnits.current
    val language by viewModel.language.collectAsState()
    val savedLocation by viewModel.savedLocation.collectAsState()
    val themeMode by viewModel.themeMode.collectAsState()

    val showGpsWarning = rememberGpsWarningState(
        savedLocation = savedLocation,
        isGpsAvailable = { viewModel.isGpsAvailable },
        events = viewModel.events
    )

    CompositionLocalProvider(LocalOverscrollFactory provides null) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            PrimaryAppBar(title = stringResource(R.string.settings))

            GpsWarningBanner(visible = showGpsWarning)

            Spacer(modifier = Modifier.height(16.dp))

            GeneralSection(
                currentLanguageCode = language.code,
                onLanguageChange = { viewModel.setLanguage(it) },
                currentProvider = savedLocation?.provider ?: LocationProvider.GPS,
                currentAddress = savedLocation?.address,
                onProviderChange = { viewModel.setLocationProvider(it) },
                onUpdateLocationClick = onUpdateLocation
            )

            Spacer(modifier = Modifier.height(24.dp))

            MeasurementUnitsSection(
                tempUnit = units.tempUnit,
                windUnit = units.windUnit,
                pressureUnit = units.pressureUnit,
                onTempUnitChange = { viewModel.setTempUnit(it) },
                onWindUnitChange = { viewModel.setWindUnit(it) },
                onPressureUnitChange = { viewModel.setPressureUnit(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            AppearanceSection(
                currentMode = themeMode,
                onModeChange = { viewModel.setThemeMode(it) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            SettingsVersion()
        }
    }
}