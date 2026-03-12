package com.iti.skypulse.ui.favorites.components.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.iti.skypulse.data.model.WeatherModel
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.theme.AppTypography

@Composable
fun FavoriteDetailStatsGrid(weather: WeatherModel) {
    val stats = listOf(
        Triple(stringResource(R.string.wind), "${weather.windSpeed} m/s", Icons.Rounded.Air),
        Triple(stringResource(R.string.humidity), "${weather.humidityPercentage}%", Icons.Rounded.WaterDrop),
        Triple(stringResource(R.string.visibility), "${"%.1f".format(weather.visibilityInMeters / 1000.0)} km", Icons.Rounded.Visibility),
        Triple(stringResource(R.string.pressure), "${weather.atmosphericPressure} hPa", Icons.Rounded.Speed),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.heightIn(max = 300.dp)
    ) {
        items(stats) { (label, value, icon) ->
            FavoriteStatCard(
                label = label,
                value = value,
                icon = icon
            )
        }
    }
}

@Composable
private fun FavoriteStatCard(
    label: String,
    value: String,
    icon: ImageVector
) {
    PrimaryCard {
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
                    style = AppTypography.semiBold18,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}