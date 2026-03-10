package com.iti.skypulse.ui.map.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme

@Composable
fun MapConfirmButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Text(
            text = text,
            style = AppTypography.semiBold18,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MapConfirmButtonPreview() {
    SkyPulseTheme {
        MapConfirmButton(
            text = "Confirm Location",
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}