package com.iti.skypulse.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme

@Composable
fun ErrorScreen(
    title: String,
    message: String? = null,
    onRetry: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_image),
            contentDescription = null,
            modifier = Modifier.size(240.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = title,
            style = AppTypography.bold20,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        if(message != null) {
            Text(
            text = message,
            style = AppTypography.medium14,
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )}

        Spacer(modifier = Modifier.height(48.dp))

        if(onRetry != null) {
            ElevatedPrimaryButton(
                text = stringResource(id = R.string.try_again),
                onClick = onRetry
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    SkyPulseTheme {
        ErrorScreen(
            title = "Something went wrong",
            message = "We couldn't retrieve the latest weather data. Please check your connection and try again.",
            onRetry = {}
        )
    }
}
