package com.iti.skypulse.ui.navigation.animation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween

fun slideInRight() = slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = tween(350)
)

fun slideOutLeft() = slideOutHorizontally(
    targetOffsetX = { -it },
    animationSpec = tween(350)
)

fun slideInLeft() = slideInHorizontally(
    initialOffsetX = { -it },
    animationSpec = tween(350)
)

fun slideOutRight() = slideOutHorizontally(
    targetOffsetX = { it },
    animationSpec = tween(350)
)