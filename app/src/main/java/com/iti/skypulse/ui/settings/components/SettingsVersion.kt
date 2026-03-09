package com.iti.skypulse.ui.settings.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iti.skypulse.BuildConfig
import com.iti.skypulse.R
import com.iti.skypulse.ui.theme.AppTypography

@Composable
fun SettingsVersion(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.version_text, BuildConfig.VERSION_NAME),
            style = AppTypography.medium12,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}