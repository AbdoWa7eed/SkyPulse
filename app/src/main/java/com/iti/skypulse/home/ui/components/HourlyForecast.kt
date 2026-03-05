package com.iti.skypulse.home.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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

        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "scale"
    )
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Unspecified,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "bg"
    )
    val textColorTime = if (isSelected) Color.White.copy(alpha = 0.7f)
    else MaterialTheme.colorScheme.onSecondary
    val textColorTemp = if (isSelected) Color.White
    else MaterialTheme.colorScheme.onBackground

    PrimaryCard(
        modifier = Modifier
            .width(76.dp)
            .scale(animatedScale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
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