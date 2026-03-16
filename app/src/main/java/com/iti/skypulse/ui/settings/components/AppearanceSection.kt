package com.iti.skypulse.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.core.utils.ThemeMode
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.DarkBackground

@Composable
fun AppearanceSection(
    currentMode: ThemeMode,
    onModeChange: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = listOf(
        Triple(ThemeMode.LIGHT, Icons.Default.LightMode, Color(0xFFEAB308)),
        Triple(ThemeMode.DARK, Icons.Default.DarkMode, Color(0xFF94A3B8)),
        Triple(ThemeMode.SYSTEM, Icons.Default.BrightnessMedium, Color(0xFF64748B))
    )

    Column(modifier = modifier) {
        SettingsSectionHeader(title = stringResource(R.string.appearance))
        PrimaryCard {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                options.forEach { (mode, icon, iconColor) ->
                    AppearanceButton(
                        modifier = Modifier.weight(1f),
                        icon = icon,
                        label = stringResource(mode.labelRes),
                        isSelected = currentMode == mode,
                        iconColor = iconColor,
                        themeMode = mode,
                        onClick = { onModeChange(mode) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AppearanceButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    iconColor: Color,
    themeMode: ThemeMode,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.5.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            ThemePreviewBackground(themeMode)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = label,
            style = if (isSelected) AppTypography.bold10 else AppTypography.medium10,
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
private fun ThemePreviewBackground(themeMode: ThemeMode) {
    when (themeMode) {
        ThemeMode.LIGHT  -> Box(modifier = Modifier.fillMaxSize().background(Color.White))
        ThemeMode.DARK   -> Box(modifier = Modifier.fillMaxSize().background(DarkBackground))
        ThemeMode.SYSTEM -> Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color.White))
            Box(modifier = Modifier.weight(1f).fillMaxHeight().background(DarkBackground))
        }
    }
}