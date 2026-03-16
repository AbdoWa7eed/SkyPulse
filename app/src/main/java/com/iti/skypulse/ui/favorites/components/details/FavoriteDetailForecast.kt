package com.iti.skypulse.ui.favorites.components.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.iti.skypulse.data.model.weather.DailyForecastModel
import com.iti.skypulse.data.model.weather.ForecastModel
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.components.WeatherIcon
import com.iti.skypulse.ui.theme.AppTypography

@Composable
fun FavoriteDetailForecast(
    forecast: ForecastModel,
    tempUnit: TempUnit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.five_day_forecast),
            style = AppTypography.semiBold18,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
        items(forecast.dailyForecasts) { day ->
            ForecastDayItem(day = day, tempUnit = tempUnit)
        }
    }
    }
}

@Composable
private fun ForecastDayItem(
    day: DailyForecastModel,
    tempUnit: TempUnit
) {
    PrimaryCard{
        Column(
            modifier = Modifier
                .widthIn(min = 90.dp)
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = day.dayName,
                style = AppTypography.regular12,
                color = MaterialTheme.colorScheme.onSecondary
            )
            WeatherIcon(
                iconCode = day.weatherIconCode,
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = UnitConverter.formatTemp(day.highTemperature, tempUnit).display(),
                style = AppTypography.medium16,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = UnitConverter.formatTemp(day.lowTemperature, tempUnit).display(),
                style = AppTypography.regular12,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}