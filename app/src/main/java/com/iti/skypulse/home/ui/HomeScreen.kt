package com.iti.skypulse.home.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.home.ui.animation.rememberHomeAnimationStep
import com.iti.skypulse.R
import com.iti.skypulse.common.PrimaryAppBar
import com.iti.skypulse.home.ui.components.CurrentWeatherCard
import com.iti.skypulse.home.ui.components.HourlyForecast
import com.iti.skypulse.home.ui.components.WeatherDetails
import com.iti.skypulse.ui.theme.SkyPulseTheme

@Composable
fun HomeScreen() {
    val animationStep by rememberHomeAnimationStep()

    val enterAnimation = fadeIn(tween(400)) + slideInVertically(tween(400)) { it / 4 }

    CompositionLocalProvider(LocalOverscrollFactory provides null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            PrimaryAppBar(
                title = stringResource(R.string.app_title),
                location = "Awsim, Giza"
            )

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                AnimatedVisibility(visible = animationStep >= 1, enter = enterAnimation) {
                    CurrentWeatherCard()
                }
                AnimatedVisibility(visible = animationStep >= 2, enter = enterAnimation) {
                    HourlyForecast()
                }
                AnimatedVisibility(visible = animationStep >= 3, enter = enterAnimation) {
                    WeatherDetails()
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