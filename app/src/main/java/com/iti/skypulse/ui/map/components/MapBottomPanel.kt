package com.iti.skypulse.ui.map.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.ui.components.ElevatedPrimaryButton
import com.iti.skypulse.ui.map.MapSelectionState

@Composable
fun MapBottomPanel(
    selectionState: MapSelectionState,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isConfirming = selectionState is MapSelectionState.Confirming
    val hasConfirmedLocation = selectionState is MapSelectionState.WeatherLoaded
            || selectionState is MapSelectionState.AddressResolved
            || selectionState is MapSelectionState.Confirming

    val enterAnimation = fadeIn(tween(400)) + slideInVertically(tween(400)) { it / 4 }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(vertical = 16.dp)
    ) {
        AnimatedVisibility(
            modifier = Modifier.padding(horizontal = 24.dp),
            visible = selectionState !is MapSelectionState.Idle,
            enter = enterAnimation
        ) {
            MapSelectionCard(
                state = selectionState,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        AnimatedVisibility(
            visible = hasConfirmedLocation,
            enter = enterAnimation
        ) {
            ElevatedPrimaryButton(
                text = stringResource(R.string.confirm_location),
                onClick = { if (!isConfirming) onConfirm() },
                modifier = Modifier
                    .fillMaxWidth(),
                content = if (isConfirming) {
                    {
                        CircularProgressIndicator(
                            modifier = Modifier.size(28.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                } else null
            )
        }
    }
}