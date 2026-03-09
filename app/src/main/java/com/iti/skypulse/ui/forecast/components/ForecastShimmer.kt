package com.iti.skypulse.ui.forecast.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iti.skypulse.ui.components.PrimaryCard
import com.iti.skypulse.ui.components.ShimmerBox

@Composable
fun ForecastShimmer() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        repeat(5) {
            ForecastCardShimmer()
        }
    }
}

@Composable
private fun ForecastCardShimmer() {
    PrimaryCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShimmerBox(
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                ShimmerBox(
                    modifier = Modifier
                        .width(100.dp)
                        .height(18.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                ShimmerBox(
                    modifier = Modifier
                        .width(60.dp)
                        .height(12.dp)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                ShimmerBox(
                    modifier = Modifier
                        .width(80.dp)
                        .height(18.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                ShimmerBox(
                    modifier = Modifier
                        .width(50.dp)
                        .height(12.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            ShimmerBox(
                modifier = Modifier.size(24.dp)
            )
        }
    }
}