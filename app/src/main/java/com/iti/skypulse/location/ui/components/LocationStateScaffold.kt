package com.iti.skypulse.location.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.iti.skypulse.ui.theme.AppTypography

@Composable
fun LocationStateScaffold(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    buttons: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon()
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = title,
            style = AppTypography.semiBold18,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = subtitle,
            style = AppTypography.medium10,
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        buttons()
    }
}