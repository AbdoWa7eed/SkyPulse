package com.iti.skypulse.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.common.PrimaryCard
import com.iti.skypulse.common.WeatherIcon
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme

data class HourlyForecastData(
    val time: String,
    val iconCode: String,
    val temperature: String
)

val sampleHourlyData = listOf(
    HourlyForecastData("Now",  "01d", "72°"),
    HourlyForecastData("2 PM", "02d", "72°"),
    HourlyForecastData("3 PM", "03d", "71°"),
    HourlyForecastData("4 PM", "10d", "69°"),
    HourlyForecastData("5 PM", "10n", "68°")
)

@Composable
fun HourlyForecast(
    items: List<HourlyForecastData> = sampleHourlyData
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.hourly_forecast),
            style = AppTypography.semiBold18,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            itemsIndexed(items) { index, item ->
                HourlyForecastItem(
                    data = item,
                    isSelected = index == selectedIndex,
                    onClick = { selectedIndex = index }
                )
            }
        }
    }
}

@Composable
fun HourlyForecastItem(
    data: HourlyForecastData,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Unspecified
    val textColorTime = if (isSelected) Color.White.copy(alpha = 0.7f)
    else MaterialTheme.colorScheme.onSecondary
    val textColorTemp = if (isSelected) Color.White
    else MaterialTheme.colorScheme.onBackground

    PrimaryCard(
        modifier = Modifier
            .width(76.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        containerColor = bgColor
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 12.dp)
        ) {
            Text(
                text = data.time,
                color = textColorTime,
                style = AppTypography.medium12
            )

            Spacer(modifier = Modifier.height(8.dp))

            WeatherIcon(
                iconCode = data.iconCode,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = data.temperature,
                color = textColorTemp,
                style = AppTypography.bold14
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HourlyForecastPreview() {
    SkyPulseTheme {
        HourlyForecast()
    }
}