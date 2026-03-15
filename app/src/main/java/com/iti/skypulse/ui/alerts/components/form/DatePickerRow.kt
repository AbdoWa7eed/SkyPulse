package com.iti.skypulse.ui.alerts.components.form

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.ui.theme.AppTypography
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DatePickerRow(
    modifier: Modifier = Modifier,
    selectedDateMillis: Long?,
    onClick: () -> Unit
) {
    PickerRow(
        modifier = modifier,
        icon = {
            Icon(
                imageVector = Icons.Rounded.CalendarMonth,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        },
        text = selectedDateMillis?.let {
            SimpleDateFormat("EEE, MMM d yyyy", Locale.getDefault()).format(Date(it))
        } ?: stringResource(R.string.pick_date),
        hasValue = selectedDateMillis != null,
        onClick = onClick
    )
}

@Composable
fun TimePickerRow(
    modifier: Modifier = Modifier,
    selectedHour: Int?,
    selectedMinute: Int?,
    onClick: () -> Unit
) {
    val timeText = if (selectedHour != null && selectedMinute != null) {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, selectedHour)
            set(Calendar.MINUTE, selectedMinute)
        }
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(cal.time)
    } else null

    PickerRow(
        modifier = modifier,
        icon = {
            Icon(
                imageVector = Icons.Rounded.Schedule,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        },
        text = timeText ?: stringResource(R.string.pick_time),
        hasValue = timeText != null,
        onClick = onClick
    )
}

@Composable
private fun PickerRow(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    text: String,
    hasValue: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        icon()
        Text(
            text = text,
            style = AppTypography.medium14,
            color = if (hasValue) MaterialTheme.colorScheme.onSurface
            else MaterialTheme.colorScheme.onSecondary
        )
    }
}