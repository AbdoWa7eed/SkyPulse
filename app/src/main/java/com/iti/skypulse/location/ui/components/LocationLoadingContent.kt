package com.iti.skypulse.location.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme

@Composable
fun LocationLoadingContent(
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    LocationStateScaffold(
        icon = {
            CircularProgressIndicator(
                modifier = Modifier.size(80.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
        },
        title = stringResource(R.string.location_fetching),
        subtitle = stringResource(R.string.location_fetching_subtitle),
        modifier = modifier
    ) {
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(stringResource(R.string.location_cancel), style = AppTypography.semiBold18)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationLoadingPreview() {
    SkyPulseTheme { LocationLoadingContent({}) }
}