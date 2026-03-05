package com.iti.skypulse.forecast.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iti.skypulse.common.WeatherIcon
import com.iti.skypulse.ui.theme.AppTypography

@Composable
fun ForecastHourlyItem(data: ForecastHourlyData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(52.dp)
    ) {
        Text(
            text = data.time,
            style = AppTypography.bold10,
            color = MaterialTheme.colorScheme.onSecondary
        )

        Spacer(modifier = Modifier.height(6.dp))

        WeatherIcon(
            iconCode = data.iconCode,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = data.temperature,
            style = AppTypography.bold14,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
