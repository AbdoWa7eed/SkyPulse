package com.iti.skypulse.ui.alerts

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

data class FullScreenIntentPermissionState(
    val isGranted: Boolean,
    val request: () -> Unit
)

@Composable
fun rememberFullScreenIntentPermissionState(
    onResult: () -> Unit
): FullScreenIntentPermissionState {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        onResult()
    }

    val isGranted = remember(context) {
        isFullScreenIntentGranted(context)
    }

    val request = remember(context) {
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                launcher.launch(
                    Intent(
                        Settings.ACTION_MANAGE_APP_USE_FULL_SCREEN_INTENT,
                        "package:${context.packageName}".toUri()
                    )
                )
            } else {
                onResult()
            }
        }
    }

    return FullScreenIntentPermissionState(
        isGranted = isGranted,
        request = request
    )
}

fun isFullScreenIntentGranted(context: Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) return true
    val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    return nm.canUseFullScreenIntent()
}