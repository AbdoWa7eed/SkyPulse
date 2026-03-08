package com.iti.skypulse.ui.home.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iti.skypulse.ui.components.ShimmerBox

@Composable
fun HomeShimmer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ShimmerBox(height = 220.dp)

        Spacer(modifier = Modifier.height(24.dp))

        ShimmerBox(height = 20.dp, cornerRadius = 8.dp)

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            repeat(4) {
                ShimmerBox(
                    modifier = Modifier.weight(1f),
                    height = 100.dp
                )
                if (it < 3) Spacer(modifier = Modifier.width(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ShimmerBox(height = 20.dp, cornerRadius = 8.dp)

        Spacer(modifier = Modifier.height(16.dp))

        repeat(2) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ShimmerBox(
                    modifier = Modifier.weight(1f),
                    height = 100.dp
                )
                Spacer(modifier = Modifier.width(16.dp))
                ShimmerBox(
                    modifier = Modifier.weight(1f),
                    height = 100.dp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}