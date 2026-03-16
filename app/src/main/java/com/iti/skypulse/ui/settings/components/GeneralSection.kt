package com.iti.skypulse.ui.settings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditLocation
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.iti.skypulse.R
import com.iti.skypulse.core.utils.Language
import com.iti.skypulse.data.model.location.LocationProvider
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.components.PrimaryTextButton

@Composable
fun GeneralSection(
    currentLanguageCode: String,
    onLanguageChange: (Language) -> Unit,
    currentProvider: LocationProvider,
    currentAddress: String?,
    onProviderChange: (LocationProvider) -> Unit,
    onUpdateLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val languages = Language.entries.toList()
    val providers = LocationProvider.entries.toList()

    Column(modifier = modifier) {
        SettingsSectionHeader(title = stringResource(R.string.general))

        PrimaryCard {
            SettingsRow(
                icon = Icons.Default.Language,
                label = stringResource(R.string.language)
            ) {
                SegmentedControl(
                    options = languages.map { stringResource(it.labelRes) },
                    selectedIndex = languages.indexOfFirst { it.code == currentLanguageCode },
                    onOptionSelected = { index -> onLanguageChange(languages[index]) }
                )
            }

            SettingsDivider()

            SettingsRow(
                icon = Icons.Default.LocationOn,
                label = stringResource(R.string.location_provider)
            ) {
                SegmentedControl(
                    options = providers.map { stringResource(it.labelRes) },
                    selectedIndex = providers.indexOf(currentProvider),
                    onOptionSelected = { index -> onProviderChange(providers[index]) }
                )
            }

            AnimatedVisibility(
                visible = currentProvider == LocationProvider.MAP,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                ) {
                    SettingsDivider()

                    SettingsRow(
                        icon = Icons.Default.EditLocation,
                        label = currentAddress ?: stringResource(R.string.update_location)
                    ) {
                        PrimaryTextButton(
                            text = stringResource(R.string.update),
                            onClick = onUpdateLocationClick)                     }
                }
            }
        }
    }
}