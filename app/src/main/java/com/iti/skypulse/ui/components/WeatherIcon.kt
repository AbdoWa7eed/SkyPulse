package com.iti.skypulse.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.toArgb
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.iti.skypulse.core.utils.buildWeatherIconUrl
import com.iti.skypulse.ui.components.transformations.WhiteToColorTransformation
import com.iti.skypulse.ui.theme.sun

private const val ICON_SCALE = 0.75f

@Composable
fun WeatherIcon(
    iconCode: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    val onSecondary = MaterialTheme.colorScheme.onSecondary

    when (iconCode) {
        "01d" -> VectorWeatherIcon(
            imageVector = Icons.Rounded.WbSunny,
            tint = MaterialTheme.colorScheme.sun,
            modifier = modifier,
            contentDescription = contentDescription
        )
        "01n" -> VectorWeatherIcon(
            imageVector = Icons.Rounded.NightsStay,
            tint = onSecondary,
            modifier = modifier,
            contentDescription = contentDescription
        )
        else -> AsyncImage(
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
}

@Composable
private fun VectorWeatherIcon(
    imageVector: ImageVector,
    tint: Color,
    modifier: Modifier,
    contentDescription: String?
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier
                .matchParentSize()
                .scale(ICON_SCALE)
        )
    }
}