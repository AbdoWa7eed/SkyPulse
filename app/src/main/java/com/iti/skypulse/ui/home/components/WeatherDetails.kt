package com.iti.skypulse.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.RemoveRedEye
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iti.skypulse.R
import com.iti.skypulse.core.utils.PressureUnit
import com.iti.skypulse.core.utils.UnitConverter
import com.iti.skypulse.core.utils.WindUnit
import com.iti.skypulse.data.model.weather.WeatherModel
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme

data class WeatherDetailData(
    val icon: ImageVector,
    val label: String,
    val value: String,
    val subtext: String
)

@Composable
fun WeatherDetails(
    weather: WeatherModel,
    windUnit: WindUnit,
    pressureUnit: PressureUnit
) {
    val items = listOf(
        WeatherDetailData(
            icon = Icons.Rounded.Air,
            label = stringResource(R.string.wind),
            value = UnitConverter.formatWind(weather.windSpeed, windUnit).display(),
            subtext = stringResource(R.string.wind_direction, weather.windDirectionDegrees)
        ),
        WeatherDetailData(
            icon = Icons.Rounded.WaterDrop,
            label = stringResource(R.string.humidity),
            value = stringResource(R.string.humidity_value, weather.humidityPercentage),
            subtext = stringResource(R.string.atmospheric_moisture)
        ),
        WeatherDetailData(
            icon = Icons.Rounded.RemoveRedEye,
            label = stringResource(R.string.visibility),
            value = stringResource(R.string.visibility_value, weather.visibilityInMeters / 1000),
            subtext = stringResource(
                if (weather.visibilityInMeters >= 10000) R.string.clear_sky
                else R.string.limited_visibility
            )
        ),
        WeatherDetailData(
            icon = Icons.Rounded.Speed,
            label = stringResource(R.string.pressure),
            value = UnitConverter.formatPressure(weather.atmosphericPressure, pressureUnit).display(),
            subtext = stringResource(R.string.atmospheric_pressure)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.weather_details),
            style = AppTypography.semiBold18,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { item ->
                    WeatherDetailCard(
                        data = item,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Composable
fun WeatherDetailCard(
    data: WeatherDetailData,
    modifier: Modifier = Modifier
) {
    PrimaryCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = data.icon,
                    contentDescription = data.label,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = data.label.uppercase(),
                    style = AppTypography.bold10.copy(
                        letterSpacing = 1.sp
                    ),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = data.value,
                style = AppTypography.semiBold16,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = data.subtext,
                style = AppTypography.medium10,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherDetailsPreview() {
    SkyPulseTheme {
        WeatherDetails(
            windUnit = WindUnit.METERS_PER_SECOND,
            pressureUnit = PressureUnit.HPA,
            weather = WeatherModel(
                temperature = 295.0,
                feelsLikeTemperature = 293.0,
                minimumTemperature = 287.0,
                maximumTemperature = 298.0,
                weatherDescription = "Partly Cloudy",
                weatherIconCode = "02d",
                windSpeed = 5.14,
                windDirectionDegrees = 180,
                humidityPercentage = 59,
                visibilityInMeters = 10000,
                atmosphericPressure = 1021,
                cityName = "Cairo",
                countryCode = "EG",
                longitude = 30.1,
                latitude = 40.5
            )
        )
    }
}