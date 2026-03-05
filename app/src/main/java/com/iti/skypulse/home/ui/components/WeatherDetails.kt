package com.iti.skypulse.home.ui.components

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
import com.iti.skypulse.common.PrimaryCard
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme

data class WeatherDetailData(
    val icon: ImageVector,
    val label: String,
    val value: String,
    val subtext: String
)

val sampleWeatherDetails = listOf(
    WeatherDetailData(Icons.Rounded.Air,          "Wind",       "12 mph",   "Direction: NW"),
    WeatherDetailData(Icons.Rounded.WaterDrop,    "Humidity",   "45 %",     "Dew point: 52°"),
    WeatherDetailData(Icons.Rounded.RemoveRedEye, "Visibility", "10 mi",    "Clear sky"),
    WeatherDetailData(Icons.Rounded.Speed,        "Pressure",   "1015 hPa", "Stable"),
)

@Composable
fun WeatherDetails(
    items: List<WeatherDetailData> = sampleWeatherDetails
) {
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
                style = AppTypography.bold20,
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
        WeatherDetails()
    }
}