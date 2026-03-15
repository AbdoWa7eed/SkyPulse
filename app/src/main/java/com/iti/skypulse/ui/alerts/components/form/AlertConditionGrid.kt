package com.iti.skypulse.ui.alerts.components.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iti.skypulse.data.model.alert.WeatherAlertType
import com.iti.skypulse.ui.alerts.components.alertIcon
import com.iti.skypulse.ui.alerts.components.alertLabel

@Composable
fun AlertConditionGrid(
    selected: WeatherAlertType?,
    onSelect: (WeatherAlertType) -> Unit
) {
    val types = WeatherAlertType.entries.toList()

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        types.chunked(3).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                row.forEach { type ->
                    AlertChip(
                        label = alertLabel(type),
                        icon = alertIcon(type),
                        selected = selected == type,
                        onClick = { onSelect(type) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}