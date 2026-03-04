package com.iti.skypulse.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun PrimaryCard(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    content: @Composable ColumnScope.() -> Unit
) {
    val finalColor = if (containerColor == Color.Unspecified) MaterialTheme.colorScheme.surface else containerColor
    Card(
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = MaterialTheme.colorScheme.primary.copy(0.1f)
            ),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = finalColor
        ),
        content = content
    )
}