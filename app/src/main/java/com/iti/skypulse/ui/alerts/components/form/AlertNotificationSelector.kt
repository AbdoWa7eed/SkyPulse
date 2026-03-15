package com.iti.skypulse.ui.alerts.components.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iti.skypulse.data.model.alert.AlertNotificationType
import com.iti.skypulse.ui.alerts.components.alertNotificationIcon
import com.iti.skypulse.ui.alerts.components.alertNotificationLabel

@Composable
fun AlertNotificationSelector(
    selected: AlertNotificationType,
    onSelect: (AlertNotificationType) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        AlertNotificationType.entries.forEach { type ->
            AlertChip(
                label = alertNotificationLabel(type),
                icon = alertNotificationIcon(type),
                selected = selected == type,
                onClick = { onSelect(type) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}