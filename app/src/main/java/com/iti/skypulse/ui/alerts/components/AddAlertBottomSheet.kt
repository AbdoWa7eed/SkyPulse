package com.iti.skypulse.ui.alerts.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.ui.alerts.AlertFormState
import com.iti.skypulse.ui.alerts.components.form.AlertConditionGrid
import com.iti.skypulse.ui.alerts.components.form.AlertNotificationSelector
import com.iti.skypulse.ui.alerts.components.form.DateTimePicker
import com.iti.skypulse.ui.components.ElevatedPrimaryButton
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlertBottomSheet(
    initialForm: AlertFormState? = null,
    onDismiss: () -> Unit,
    onConfirm: (AlertFormState) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var form by remember { mutableStateOf(initialForm ?: AlertFormState()) }
    var showPastError by remember { mutableStateOf(false) }
    var showEndBeforeStartError by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(48.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = stringResource(R.string.add_alert),
                style = AppTypography.semiBold18,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Start Time
            Text(
                text = stringResource(R.string.alert_start_time),
                style = AppTypography.regular12,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            DateTimePicker(
                dateMillis = form.dateMillis,
                hour = form.hour,
                minute = form.minute,
                onDateSelected = {
                    form = form.copy(dateMillis = it)
                    showPastError = false
                    showEndBeforeStartError = false
                },
                onTimeSelected = { hour, minute ->
                    form = form.copy(hour = hour, minute = minute)
                    showPastError = false
                    showEndBeforeStartError = false
                }
            )

            AnimatedVisibility(visible = showPastError) {
                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = stringResource(R.string.error_past_time),
                    style = AppTypography.regular12,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // End Time
            Text(
                text = stringResource(R.string.alert_end_time),
                style = AppTypography.regular12,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            DateTimePicker(
                dateMillis = form.endDateMillis,
                hour = form.endHour,
                minute = form.endMinute,
                onDateSelected = {
                    form = form.copy(endDateMillis = it)
                    showEndBeforeStartError = false
                },
                onTimeSelected = { hour, minute ->
                    form = form.copy(endHour = hour, endMinute = minute)
                    showEndBeforeStartError = false
                }
            )

            AnimatedVisibility(visible = showEndBeforeStartError) {
                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = stringResource(R.string.error_end_before_start),
                    style = AppTypography.regular12,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.alert_condition),
                style = AppTypography.regular12,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            AlertConditionGrid(
                selected = form.type,
                onSelect = { form = form.copy(type = it) }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.alert_notification_type),
                style = AppTypography.regular12,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            AlertNotificationSelector(
                selected = form.notificationType,
                onSelect = { form = form.copy(notificationType = it) }
            )

            Spacer(modifier = Modifier.height(20.dp))
            ElevatedPrimaryButton(
                padding = PaddingValues(horizontal = 0.dp, vertical = 12.dp),
                text = stringResource(R.string.add_alert),
                enabled = form.isValid,
                onClick = {
                    when {
                        !form.isInFuture -> {
                            showPastError = true
                        }
                        !form.isEndAfterStart -> {
                            showEndBeforeStartError = true
                        }
                        else -> {
                            showPastError = false
                            showEndBeforeStartError = false
                            onConfirm(form)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun AddAlertSheetPreview() {
    SkyPulseTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            AddAlertBottomSheet(onDismiss = {}, onConfirm = {})
        }
    }
}