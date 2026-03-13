package com.iti.skypulse.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.iti.skypulse.core.utils.ThemeMode

val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = AccentCyan,
    tertiary = PrimaryDark,
    background = DarkBackground,
    surface = DarkCard,
    onPrimary = TextPrimary,
    onSecondary = TextSecondary,
    onTertiary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    error = Danger,
    onError = TextPrimary,
    surfaceVariant = Warning,
    onSurfaceVariant = TextPrimary,
)

val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = AccentCyan,
    tertiary = PrimaryDark,
    background = LightBackground,
    surface = LightCard,
    onPrimary = TextPrimary,
    onSecondary = TextSecondary,
    onTertiary = TextPrimaryLight,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,
    error = Danger,
    onError = TextPrimary,
    surfaceVariant = WarningLight,
    onSurfaceVariant = TextPrimaryLight,
)

@Composable
private fun animateColorScheme(target: ColorScheme): ColorScheme {
    val anim = tween<Color>(durationMillis = 400)
    return ColorScheme(
        primary = animateColorAsState(target.primary, anim).value,
        onPrimary = animateColorAsState(target.onPrimary, anim).value,
        primaryContainer = animateColorAsState(target.primaryContainer, anim).value,
        onPrimaryContainer = animateColorAsState(target.onPrimaryContainer, anim).value,
        inversePrimary = animateColorAsState(target.inversePrimary, anim).value,
        secondary = animateColorAsState(target.secondary, anim).value,
        onSecondary = animateColorAsState(target.onSecondary, anim).value,
        secondaryContainer = animateColorAsState(target.secondaryContainer, anim).value,
        onSecondaryContainer = animateColorAsState(target.onSecondaryContainer, anim).value,
        tertiary = animateColorAsState(target.tertiary, anim).value,
        onTertiary = animateColorAsState(target.onTertiary, anim).value,
        tertiaryContainer = animateColorAsState(target.tertiaryContainer, anim).value,
        onTertiaryContainer = animateColorAsState(target.onTertiaryContainer, anim).value,
        background = animateColorAsState(target.background, anim).value,
        onBackground = animateColorAsState(target.onBackground, anim).value,
        surface = animateColorAsState(target.surface, anim).value,
        onSurface = animateColorAsState(target.onSurface, anim).value,
        surfaceVariant = animateColorAsState(target.surfaceVariant, anim).value,
        onSurfaceVariant = animateColorAsState(target.onSurfaceVariant, anim).value,
        surfaceTint = animateColorAsState(target.surfaceTint, anim).value,
        inverseSurface = animateColorAsState(target.inverseSurface, anim).value,
        inverseOnSurface = animateColorAsState(target.inverseOnSurface, anim).value,
        error = animateColorAsState(target.error, anim).value,
        onError = animateColorAsState(target.onError, anim).value,
        errorContainer = animateColorAsState(target.errorContainer, anim).value,
        onErrorContainer = animateColorAsState(target.onErrorContainer, anim).value,
        outline = animateColorAsState(target.outline, anim).value,
        outlineVariant = animateColorAsState(target.outlineVariant, anim).value,
        scrim = animateColorAsState(target.scrim, anim).value,
    )
}

@Composable
fun SkyPulseTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT  -> false
        ThemeMode.DARK   -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val targetScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val colorScheme = animateColorScheme(target = targetScheme)
    val warningColors = if (darkTheme) {
        WarningColors(warning = Warning, onWarning = WarningDark)
    } else {
        WarningColors(warning = Warning, onWarning = WarningLight)
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalWarningColors provides warningColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }

}