package com.iti.skypulse.ui.home.components

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
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.UnitConverter
import com.iti.skypulse.data.model.weather.HourlyForecastModel
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.components.WeatherIcon
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme

data class HourlyForecastData(
    val time: String,
    val iconCode: String,
    val temperature: String
)


@Composable
fun HourlyForecast(
    items: List<HourlyForecastModel>,
    tempUnit: TempUnit

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
                    data = HourlyForecastData(
                        time = item.time,
                        iconCode = item.weatherIconCode,
                        temperature = UnitConverter
                            .formatTemp(item.temperature, tempUnit).display()
                    ),
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
            .wrapContentWidth()
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
        HourlyForecast(
            tempUnit = TempUnit.CELSIUS,
            items = listOf(
                HourlyForecastModel(time = "Now",   weatherIconCode = "01d", temperature = 22.0),
                HourlyForecastModel(time = "14:00", weatherIconCode = "02d", temperature = 21.0),
                HourlyForecastModel(time = "15:00", weatherIconCode = "03d", temperature = 20.0),
                HourlyForecastModel(time = "16:00", weatherIconCode = "10d", temperature = 19.0),
                HourlyForecastModel(time = "17:00", weatherIconCode = "10n", temperature = 18.0),
            )
        )
    }
}