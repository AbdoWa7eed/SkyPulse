package com.iti.skypulse.ui.alerts.components.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.ui.theme.AppTypography
import java.util.Calendar

@Composable
fun DateTimePicker(
    dateMillis: Long?,
    hour: Int?,
    minute: Int?,
    onDateSelected: (Long?) -> Unit,
    onTimeSelected: (Int, Int) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val calendar = remember { Calendar.getInstance() }

    if (showDatePicker) {
        DatePickerDialog(
            initialDateMillis = dateMillis ?: System.currentTimeMillis(),
            onDismiss = { showDatePicker = false },
            onConfirm = {
                onDateSelected(it)
                showDatePicker = false
            }
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            initialHour = hour ?: calendar.get(Calendar.HOUR_OF_DAY),
            initialMinute = minute ?: calendar.get(Calendar.MINUTE),
            onDismiss = { showTimePicker = false },
            onConfirm = { h, m ->
                onTimeSelected(h, m)
                showTimePicker = false
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(modifier = Modifier.weight(1f).fillMaxHeight()) {
            Text(
                text = stringResource(R.string.alert_date),
                style = AppTypography.regular12,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            DatePickerRow(
                selectedDateMillis = dateMillis,
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxHeight()
            )
        }
        Column(modifier = Modifier.weight(1f).fillMaxHeight()) {
            Text(
                text = stringResource(R.string.alert_time),
                style = AppTypography.regular12,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            TimePickerRow(
                selectedHour = hour,
                selectedMinute = minute,
                onClick = { showTimePicker = true },
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}