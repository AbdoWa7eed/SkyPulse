package com.iti.skypulse.ui.alarms.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.iti.skypulse.core.extensions.toFormattedDateTime
import com.iti.skypulse.data.model.WeatherAlert
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.components.SwipeToDeleteBox
import com.iti.skypulse.ui.theme.AppTypography

@Composable
fun WeatherAlertCard(
    alert: WeatherAlert,
    onToggle: (Boolean) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tint = if (alert.isEnabled) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.onSecondary

    SwipeToDeleteBox(onDelete = onDelete, modifier = modifier) {
        PrimaryCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(tint.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = alertIcon(alert.type),
                        contentDescription = null,
                        tint = tint,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = alertLabel(alert.type),
                        style = AppTypography.semiBold16,
                        color = if (alert.isEnabled) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSecondary
                    )
                    Text(
                        text = alert.scheduledTime.toFormattedDateTime(),
                        style = AppTypography.regular12,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = alertNotificationIcon(alert.notificationType),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = alertNotificationLabel(alert.notificationType),
                            style = AppTypography.regular12,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                Switch(
                    checked = alert.isEnabled,
                    onCheckedChange = onToggle,
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }
    }
}