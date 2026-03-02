package com.iti.skypulse.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

val slideOutLeft = slideOutHorizontally(
    targetOffsetX = { -it },
    animationSpec = tween(500)
)

val slideInRight = slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = tween(500)
)

