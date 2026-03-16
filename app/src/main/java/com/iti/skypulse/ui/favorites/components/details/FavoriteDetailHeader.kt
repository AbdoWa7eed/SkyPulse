package com.iti.skypulse.ui.favorites.components.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.UnitConverter
import com.iti.skypulse.ui.components.WeatherIcon
import com.iti.skypulse.ui.favorites.FavoriteLocationItem
import com.iti.skypulse.ui.home.components.TempPill
import com.iti.skypulse.ui.theme.AppTypography

@Composable
fun FavoriteDetailHeader(
    item: FavoriteLocationItem,
    tempUnit: TempUnit,
) {
    val weather = item.weather

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = weather.cityName,
                style = AppTypography.semiBold18,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = weather.weatherDescription.replaceFirstChar { it.uppercaseChar() },
                style = AppTypography.regular12,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = UnitConverter.formatTemp(weather.temperature, tempUnit).display(),
                style = AppTypography.bold36,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(
                    R.string.feels_like,
                    UnitConverter.formatTemp(weather.feelsLikeTemperature, tempUnit).display()
                ),
                style = AppTypography.regular12,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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

        WeatherIcon(
            iconCode = weather.weatherIconCode,
            modifier = Modifier.size(80.dp)
        )
    }
}