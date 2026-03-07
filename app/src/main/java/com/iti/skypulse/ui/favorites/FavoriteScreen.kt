package com.iti.skypulse.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.ui.theme.SkyPulseTheme
import com.iti.skypulse.ui.theme.AppTypography

@Composable
fun FavoriteLocationsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Favorite Locations Screen",
            modifier = Modifier.padding(20.dp),
            textAlign = TextAlign.Center,
            style = AppTypography.semiBold24,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteLocationsScreenPreview() {
    SkyPulseTheme {
        FavoriteLocationsScreen()
    }
}