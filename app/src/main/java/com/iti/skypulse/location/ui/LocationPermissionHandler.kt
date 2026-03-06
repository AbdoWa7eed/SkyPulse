package com.iti.skypulse.location.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.iti.skypulse.location.ui.components.LocationDialogType

@Composable
fun rememberLocationPermissionHandler(
    onGranted: () -> Unit,
    onDenied: () -> Unit
): () -> Unit {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
                || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) onGranted() else onDenied()
    }

    return remember {
        {
            launcher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}

fun buildSettingsIntent(context: Context, type: LocationDialogType): Intent =
    when (type) {
        LocationDialogType.PERMISSION_DENIED -> Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        ).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        LocationDialogType.LOCATION_DISABLED ->
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    }