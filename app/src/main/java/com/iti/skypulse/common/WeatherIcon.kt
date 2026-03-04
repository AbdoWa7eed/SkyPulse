package com.iti.skypulse.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.graphics.toArgb
import com.iti.skypulse.common.transformations.WhiteToColorTransformation

fun buildWeatherIconUrl(iconCode: String): String =
    "https://openweathermap.org/img/wn/${iconCode}@2x.png"

@Composable
fun WeatherIcon(
    iconCode: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    val onSecondary = MaterialTheme.colorScheme.onSecondary

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(buildWeatherIconUrl(iconCode))
            .crossfade(true)
            .transformations(WhiteToColorTransformation(onSecondary.toArgb()))
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}

