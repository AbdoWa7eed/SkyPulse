package com.iti.skypulse.ui.favorites.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.core.utils.UnitConverter
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.components.SwipeToDeleteBox
import com.iti.skypulse.ui.components.WeatherIcon
import com.iti.skypulse.ui.favorites.FavoriteLocationItem
import com.iti.skypulse.ui.theme.AppTypography
@Composable
fun FavoriteLocationCard(
    item: FavoriteLocationItem,
    tempUnit: TempUnit,
    onDismiss: () -> Unit,
    onClick: (FavoriteLocationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    SwipeToDeleteBox(
        onDelete = onDismiss,
        modifier = modifier
    ) {
        PrimaryCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(item) }
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = item.weather.cityName,
                        style = AppTypography.semiBold18,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.weather.weatherDescription.replaceFirstChar { it.uppercaseChar() },
                        style = AppTypography.regular14,
                        color = MaterialTheme.colorScheme.onSecondary,
                        maxLines = 1
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    WeatherIcon(iconCode = item.weather.weatherIconCode, modifier = Modifier.size(44.dp))
                    Text(
                        text = UnitConverter.formatTemp(item.weather.temperature, tempUnit).display(),
                        style = AppTypography.regular16,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
