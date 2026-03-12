package com.iti.skypulse.ui.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iti.skypulse.R
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.UnitConverter
import com.iti.skypulse.data.model.LocationProvider
import com.iti.skypulse.data.model.SavedLocation
import com.iti.skypulse.data.model.WeatherModel
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.map.MapSelectionState
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme

@Composable
fun MapSelectionCard(
    state: MapSelectionState,
    modifier: Modifier = Modifier
) {
    PrimaryCard(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectionIconBadge()

            Column(modifier = Modifier.weight(1f)) {
                SelectionLabel()
                Spacer(modifier = Modifier.height(4.dp))
                SelectionAddressSlot(state)
            }

            SelectionWeatherSlot(state)
        }
    }
}

@Composable
private fun SelectionIconBadge() {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.LocationOn,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun SelectionLabel() {
    Text(
        text = stringResource(R.string.current_selection).uppercase(),
        style = AppTypography.bold10.copy(letterSpacing = 1.sp),
        color = MaterialTheme.colorScheme.onSecondary
    )
}

@Composable
private fun SelectionAddressSlot(state: MapSelectionState) {
    when (state) {
        is MapSelectionState.ResolvingAddress -> SelectionLoadingIndicator()
        is MapSelectionState.AddressResolved  -> AddressText(state.location)
        is MapSelectionState.WeatherLoaded    -> AddressText(state.location)
        else                                  -> Unit
    }
}

@Composable
private fun SelectionWeatherSlot(state: MapSelectionState) {
    if (state is MapSelectionState.Idle || state is MapSelectionState.ResolvingAddress) return
    SelectionDivider()
    when (state) {
        is MapSelectionState.AddressResolved -> SelectionLoadingIndicator()
        is MapSelectionState.WeatherLoaded   -> Text(
            text = UnitConverter.formatTemp(state.weather.temperature, state.tempUnit).display(),
            style = AppTypography.medium14,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun AddressText(location: SavedLocation) {
    Text(
        text = location.address ?: "${location.lat}, ${location.lng}",
        style = AppTypography.medium14,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun SelectionDivider() {
    VerticalDivider(
        modifier = Modifier.height(36.dp),
        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f)
    )
}

@Composable
private fun SelectionLoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(16.dp),
        strokeWidth = 2.dp,
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(showBackground = true)
@Composable
private fun WeatherLoadedPreview() {
    SkyPulseTheme {
        MapSelectionCard(
            state = MapSelectionState.WeatherLoaded(
                location = SavedLocation(30.0444, 31.2357, LocationProvider.MAP, "Cairo, Egypt"),
                weather = WeatherModel(
                    temperature = 305.0, feelsLikeTemperature = 307.0,
                    minimumTemperature = 300.0, maximumTemperature = 308.0,
                    weatherDescription = "Clear Sky", weatherIconCode = "01d",
                    windSpeed = 3.0, windDirectionDegrees = 90,
                    humidityPercentage = 30, visibilityInMeters = 10000,
                    atmosphericPressure = 1010, cityName = "Cairo",
                    countryCode = "EG",
                    longitude = 30.1,
                    latitude = 40.5
                ),
                tempUnit = TempUnit.CELSIUS
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}