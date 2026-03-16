package com.iti.skypulse.ui.settings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.LocalWarningColors

@Composable
fun GpsWarningBanner(
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    val warningColors = LocalWarningColors.current

    AnimatedVisibility(
        visible = visible,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
        modifier = modifier.fillMaxSize()
    ) {
        PrimaryCard(
            modifier = Modifier
                .fillMaxWidth(),
            containerColor = warningColors.warning
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = null,
                    tint = warningColors.onWarning,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = stringResource(R.string.gps_unavailable_warning),
                    style = AppTypography.regular12,
                    color = warningColors.onWarning
                )
            }
        }
    }
}