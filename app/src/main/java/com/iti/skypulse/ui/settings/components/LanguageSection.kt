package com.iti.skypulse.ui.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.iti.skypulse.R
import com.iti.skypulse.ui.components.PrimaryCard

@Composable
fun LanguageSection(
    currentLanguageCode: String,
    onLanguageChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val languages = listOf(
        "en" to stringResource(R.string.english),
        "ar" to stringResource(R.string.arabic)
    )

    Column(modifier = modifier) {
        SettingsSectionHeader(title = stringResource(R.string.general))
        PrimaryCard {
            SettingsRow(
                icon = Icons.Default.Language,
                label = stringResource(R.string.language)
            ) {
                SegmentedControl(
                    options = languages.map { it.second },
                    selectedIndex = languages.indexOfFirst { it.first == currentLanguageCode },
                    onOptionSelected = { onLanguageChange(languages[it].first) }
                )
            }
        }
    }
}