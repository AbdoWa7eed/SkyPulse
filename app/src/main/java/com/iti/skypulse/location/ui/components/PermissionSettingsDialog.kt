package com.iti.skypulse.location.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.iti.skypulse.R
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme

enum class LocationDialogType {
    PERMISSION_DENIED,
    LOCATION_DISABLED
}

@Composable
fun LocationSettingsDialog(
    type: LocationDialogType,
    onOpenSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    val title = stringResource(
        when (type) {
            LocationDialogType.PERMISSION_DENIED -> R.string.permission_required_title
            LocationDialogType.LOCATION_DISABLED -> R.string.location_disabled_title
        }
    )
    val message = stringResource(
        when (type) {
            LocationDialogType.PERMISSION_DENIED -> R.string.permission_required_message
            LocationDialogType.LOCATION_DISABLED -> R.string.location_disabled_message
        }
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.errorContainer,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .padding(14.dp)
                            .size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = title,
                    style = AppTypography.semiBold18,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = message,
                    style = AppTypography.medium10,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onOpenSettings,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.open_settings), style = AppTypography.semiBold18)
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PermissionDeniedDialogPreview() {
    SkyPulseTheme {
        LocationSettingsDialog(
            type = LocationDialogType.PERMISSION_DENIED,
            onOpenSettings = {},
            onDismiss = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocationDisabledDialogPreview() {
    SkyPulseTheme {
        LocationSettingsDialog(
            type = LocationDialogType.LOCATION_DISABLED,
            onOpenSettings = {},
            onDismiss = {}
        )
    }
}