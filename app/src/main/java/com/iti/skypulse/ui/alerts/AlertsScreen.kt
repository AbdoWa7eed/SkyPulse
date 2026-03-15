package com.iti.skypulse.ui.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iti.skypulse.R
import com.iti.skypulse.ui.alerts.components.AddAlertBottomSheet
import com.iti.skypulse.ui.alerts.components.EmptyAlertsState
import com.iti.skypulse.ui.alerts.components.WeatherAlertCard
import com.iti.skypulse.ui.components.PrimaryAppBar
import com.iti.skypulse.ui.theme.SkyPulseTheme

@Composable
fun AlertsScreen(
    viewModel: AlertsViewModel = viewModel(factory = AlertsViewModelFactory())
) {
    val alerts by viewModel.alerts.collectAsState()
    val showAddSheet by viewModel.showAddSheet.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    var pendingForm by remember { mutableStateOf<AlertFormState?>(null) }

    val fullScreenPermission = rememberFullScreenIntentPermissionState(
        onResult = {
            pendingForm?.let { viewModel.saveAlertAfterPermission(it) }
            pendingForm = null
        }
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is AlertsEvent.AlertTimeExpired -> {
                    snackbarHostState.showSnackbar(context.getString(event.messageRes))
                }
                AlertsEvent.RequestAlarmPermission -> {
                    if (fullScreenPermission.isGranted) {
                        pendingForm?.let { viewModel.saveAlertAfterPermission(it) }
                        pendingForm = null
                    } else {
                        fullScreenPermission.request()
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            PrimaryAppBar(title = stringResource(R.string.alerts))

            if (alerts.isEmpty()) {
                EmptyAlertsState()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 120.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(count = alerts.size, key = { alerts[it].id }) { index ->
                        val alert = alerts[index]
                        WeatherAlertCard(
                            alert = alert,
                            onToggle = { viewModel.toggleAlert(alert.id, it) },
                            onDelete = { viewModel.deleteAlert(alert.id) },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = viewModel::openAddSheet,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = stringResource(R.string.add_alert))
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 8.dp, vertical = 16.dp)
        )
    }


    if (showAddSheet) {
        AddAlertBottomSheet(
            onDismiss = viewModel::closeAddSheet,
            onConfirm  = { form ->
                pendingForm = form
                viewModel.addAlert(form)
            }
        )
    }


}

@Preview(showBackground = true, name = "Empty")
@Composable
private fun AlertsEmptyPreview() {
    SkyPulseTheme { AlertsScreen() }
}
