package com.iti.skypulse.ui.splash.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashAnimationController(private val scope: CoroutineScope) {
    val logoAlpha = Animatable(0f)
    val logoOffsetY = Animatable(SplashConstants.LOGO_ANIM_OFFSET)
    val textAlpha = Animatable(0f)
    val textOffsetY = Animatable(SplashConstants.TEXT_ANIM_OFFSET)

    fun startAnimations(onFinished: () -> Unit) {
        scope.launch {
            launch {
                logoAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = SplashConstants.LOGO_ANIM_DURATION)
                )
            }
            launch {
                logoOffsetY.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = SplashConstants.LOGO_ANIM_DURATION,
                        easing = FastOutSlowInEasing
                    )
                )
            }

            delay(SplashConstants.TEXT_ANIM_DELAY)
            launch {
                textAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = SplashConstants.TEXT_ANIM_DURATION)
                )
            }
            launch {
                textOffsetY.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = SplashConstants.TEXT_ANIM_DURATION,
                        easing = FastOutSlowInEasing
                    )
                )
            }

            delay(SplashConstants.SPLASH_TOTAL_DURATION)
            onFinished()
        }
    }
}