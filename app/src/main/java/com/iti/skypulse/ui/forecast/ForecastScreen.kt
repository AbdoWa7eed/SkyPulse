package com.iti.skypulse.ui.forecast

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.ui.components.PrimaryAppBar
import com.iti.skypulse.ui.forecast.components.DailyForecastData
import com.iti.skypulse.ui.forecast.components.DayForecastCard
import com.iti.skypulse.ui.forecast.components.sampleDailyForecast
import com.iti.skypulse.ui.theme.SkyPulseTheme

@Composable
fun ForecastScreen(
    days: List<DailyForecastData> = sampleDailyForecast
) {

    var expandedIndex by remember { mutableIntStateOf(0) }

    CompositionLocalProvider(
        LocalOverscrollFactory provides null
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {
                PrimaryAppBar(
                    title = stringResource(R.string.upcoming_forecast),
                    location = "Awsim, Giza"
                )
            }

            itemsIndexed(days) { index, day ->
                DayForecastCard(
                    data = day,
                    isExpanded = index == expandedIndex,
                    onToggle = { expandedIndex = if (expandedIndex == index) -1 else index },
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }

            item {
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .height(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ForecastScreenPreview() {
    SkyPulseTheme {
        ForecastScreen()
    }
}