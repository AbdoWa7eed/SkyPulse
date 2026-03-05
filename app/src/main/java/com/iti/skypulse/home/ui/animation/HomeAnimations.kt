package com.iti.skypulse.home.ui.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

@Composable
fun rememberHomeAnimationStep(
    steps: Int = 3,
    delayBetween: Long = 150L
): State<Int> {
    val animationStep = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        repeat(steps) { index ->
            animationStep.intValue = index + 1
            if (index < steps - 1) delay(delayBetween)
        }
    }

    return animationStep
}