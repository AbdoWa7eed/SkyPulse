package com.iti.skypulse.ui.favorites.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.ui.favorites.FavoriteLocationItem

@Composable
fun FavoriteLocationsList(
    items: List<FavoriteLocationItem>,
    tempUnit: TempUnit,
    onRemove: (FavoriteLocationItem) -> Unit,
    onClickItem:  (FavoriteLocationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),

    ) {
        items(
            items = items,
            key = {it.uid}
        ) { item ->
            FavoriteLocationCard(
                item = item,
                tempUnit = tempUnit,
                onDismiss = { onRemove(item) },
                onClick = onClickItem,
                modifier = Modifier.animateItem()
            )
        }
    }
}