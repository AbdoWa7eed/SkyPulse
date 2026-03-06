package com.iti.skypulse.navigation.ui

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween

fun slideOutLeft() = slideOutHorizontally(
    targetOffsetX = { -it },
    animationSpec = tween(500)
)

fun slideInRight() = slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = tween(500)
)