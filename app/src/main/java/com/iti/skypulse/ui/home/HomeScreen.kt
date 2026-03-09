package com.iti.skypulse.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iti.skypulse.ui.home.animation.rememberHomeAnimationStep
import com.iti.skypulse.R
import com.iti.skypulse.ui.components.ErrorScreen
import com.iti.skypulse.ui.components.PrimaryAppBar
import com.iti.skypulse.ui.home.components.CurrentWeatherCard
import com.iti.skypulse.ui.home.components.HomeShimmer
import com.iti.skypulse.ui.home.components.HourlyForecast
import com.iti.skypulse.ui.home.components.WeatherDetails
import com.iti.skypulse.ui.theme.SkyPulseTheme
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())
) {
    val uiState by viewModel.uiState.collectAsState()
    val animationStep by rememberHomeAnimationStep()
    val enterAnimation =
        fadeIn(tween(400)) +
                slideInVertically(tween(400)) { it / 4 }

    CompositionLocalProvider(LocalOverscrollFactory provides null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            PrimaryAppBar(
                title = stringResource(R.string.app_title),
                location = when (val state = uiState) {
                    is HomeUiState.Success -> state.weather.cityName
                    else -> null
                }
            )

            when (val state = uiState) {
                is HomeUiState.Loading -> HomeShimmer()

                is HomeUiState.Error -> {
                    ErrorScreen(
                        title = stringResource(id = R.string.error_message_title),
                        message = stringResource(id = R.string.error_message_description),
                        onRetry = { viewModel.loadWeather() }
                    )
                }

                is HomeUiState.Success -> {
                    val tempUnit by viewModel.tempUnit.collectAsState()
                    val pressureUnit by viewModel.pressureUnit.collectAsState()
                    val windUnit by viewModel.windUnit.collectAsState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp)
                    ) {
                        AnimatedVisibility(visible = animationStep >= 1, enter = enterAnimation) {
                            CurrentWeatherCard(weather = state.weather, tempUnit= tempUnit)
                        }
                        AnimatedVisibility(visible = animationStep >= 2, enter = enterAnimation) {
                            HourlyForecast(items = state.hourlyForecasts, tempUnit= tempUnit)
                        }
                        AnimatedVisibility(visible = animationStep >= 3, enter = enterAnimation) {
                            WeatherDetails(
                                weather = state.weather,
                                pressureUnit = pressureUnit,
                                windUnit = windUnit
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    SkyPulseTheme {
        HomeScreen()
    }
}