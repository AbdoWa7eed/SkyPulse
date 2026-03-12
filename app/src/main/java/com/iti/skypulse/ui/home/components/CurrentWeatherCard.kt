package com.iti.skypulse.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iti.skypulse.data.model.WeatherModel
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.components.WeatherIcon
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.UnitConverter


@Composable
fun CurrentWeatherCard(
    weather: WeatherModel,
    modifier: Modifier = Modifier,
    tempUnit: TempUnit
) {
    PrimaryCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherIcon(
                iconCode = weather.weatherIconCode,
                contentDescription = weather.weatherDescription,
                modifier = Modifier.size(96.dp).padding(bottom = 8.dp)
            )

            Text(
                text = UnitConverter.formatTemp(weather.temperature, tempUnit).display(),
                style = AppTypography.bold56.copy(letterSpacing = (-2).sp),
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = weather.weatherDescription,
                style = AppTypography.medium16,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )

            Text(
                text = UnitConverter.formatTemp(weather.feelsLikeTemperature, tempUnit).display(),
                style = AppTypography.regular12,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TempPill(
                    label = UnitConverter.formatTemp(weather.maximumTemperature, tempUnit).display(),
                    isHigh = true
                )
                TempPill(
                    label = UnitConverter.formatTemp(weather.minimumTemperature, tempUnit).display(),
                    isHigh = false
                )
            }
        }
    }
}
@Composable
fun TempPill(label: String, isHigh: Boolean) {
    val icon = if (isHigh) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward
    val color = if (isHigh) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary

    Surface(
        shape = RoundedCornerShape(50),
        color = color.copy(alpha = 0.1f),
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = if (isHigh) "High" else "Low",
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = label,
                style = AppTypography.medium14,
                color = color
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    SkyPulseTheme {
        CurrentWeatherCard(
            tempUnit = TempUnit.CELSIUS,
            weather = WeatherModel(
                temperature = 295.0,
                feelsLikeTemperature = 293.0,
                minimumTemperature = 287.0,
                maximumTemperature = 298.0,
                weatherDescription = "Partly Cloudy",
                weatherIconCode = "02d",
                windSpeed = 5.0,
                windDirectionDegrees = 180,
                humidityPercentage = 60,
                visibilityInMeters = 10000,
                atmosphericPressure = 1015,
                cityName = "Cairo",
                countryCode = "EG",
                longitude = 30.1,
                latitude = 40.5
            )
        )
    }
}
