package com.iti.skypulse.ui.favorites.components.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.core.utils.UnitConverter
import com.iti.skypulse.data.model.weather.WeatherModel
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.AppUnits

@Composable
fun FavoriteDetailStatsGrid(
    weather: WeatherModel,
    units: AppUnits
) {
    val stats = listOf(
        Triple(
            stringResource(R.string.wind),
            UnitConverter
                .formatWind(weather.windSpeed, units.windUnit).display(),
            Icons.Rounded.Air
        ),
        Triple(
            stringResource(R.string.humidity),
            stringResource(R.string.humidity_value, weather.humidityPercentage),
            Icons.Rounded.WaterDrop
        ),
        Triple(
            stringResource(R.string.visibility),
            stringResource(R.string.visibility_value, weather.visibilityInMeters / 1000),
            Icons.Rounded.Visibility
        ),
        Triple(
            stringResource(R.string.pressure),
            UnitConverter
                .formatPressure(weather.atmosphericPressure, units.pressureUnit).display(),
            Icons.Rounded.Speed
        ),
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        stats.chunked(2).forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { (label, value, icon) ->
                    FavoriteStatCard(
                        label = label,
                        value = value,
                        icon = icon,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoriteStatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector
) {
    PrimaryCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = label,
                    style = AppTypography.regular12,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = value,
                    style = AppTypography.medium16,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}