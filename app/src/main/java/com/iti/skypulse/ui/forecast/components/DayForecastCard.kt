package com.iti.skypulse.ui.forecast.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.core.utils.ConvertedValue
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.UnitConverter
import com.iti.skypulse.data.model.weather.DailyForecastModel
import com.iti.skypulse.data.model.weather.HourlyForecastModel
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.components.WeatherIcon
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme
@Composable
fun DayForecastCard(
    data: DailyForecastModel,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    formatTemp: (Double) -> ConvertedValue,
    modifier: Modifier = Modifier
) {
    PrimaryCard(
        modifier = modifier.fillMaxWidth()
    ) {
        val displayTemp =
            "${formatTemp(data.highTemperature).displayValue()} / ${formatTemp(data.lowTemperature).displayValue()}"
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                WeatherIcon(
                    iconCode = data.weatherIconCode,
                    contentDescription = data.weatherDescription,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        text = data.dayName,
                        style = AppTypography.semiBold16,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = data.date,
                        style = AppTypography.medium12,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }

                Column(
                    modifier = Modifier.weight(1.5f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = displayTemp,
                        style = AppTypography.medium14,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1
                    )
                    Text(
                        text = data.weatherDescription,
                        maxLines = 2,
                        style = AppTypography.medium12,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.End
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onToggle() }
                )
            }

            if (isExpanded) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.15f)
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    itemsIndexed(data.hourlyForecasts) { _, item ->
                        ForecastHourlyItem(
                            data = item,
                            formatTemp = formatTemp
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DayForecastCardPreview() {
    SkyPulseTheme {
        DayForecastCard(
            data = DailyForecastModel(
                dayName = "Sunday",
                date = "Mar 08",
                highTemperature = 298.0,
                lowTemperature = 287.0,
                weatherDescription = "Partly Cloudy",
                weatherIconCode = "02d",
                hourlyForecasts = listOf(
                    HourlyForecastModel(time = "12:00", weatherIconCode = "02d", temperature = 295.0),
                    HourlyForecastModel(time = "15:00", weatherIconCode = "02d", temperature = 297.0),
                    HourlyForecastModel(time = "18:00", weatherIconCode = "02n", temperature = 293.0),
                )
            ),
            isExpanded = true,
            onToggle = {},
            formatTemp = { kelvin -> UnitConverter.formatTemp(kelvin, TempUnit.CELSIUS) }
        )
    }
}