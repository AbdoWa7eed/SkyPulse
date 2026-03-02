package com.iti.skypulse.splash.ui

import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iti.skypulse.R
import com.iti.skypulse.navigation.NavRoutes
import com.iti.skypulse.splash.ui.animation.SplashAnimationController
import com.iti.skypulse.splash.ui.animation.SplashConstants
import com.iti.skypulse.splash.viewmodel.SplashViewModel
import com.iti.skypulse.splash.viewmodel.SplashViewModelFactory


@Composable
fun AnimatedSplashScreen(modifier: Modifier = Modifier, onFinished: (NavRoutes) -> Unit) {
    val viewModel: SplashViewModel = viewModel(factory = SplashViewModelFactory())
    val scope = rememberCoroutineScope()
    val controller = remember { SplashAnimationController(scope) }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { destination ->
            controller.startAnimations {
                onFinished(destination)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.offset(y = SplashConstants.COLUMN_OFFSET_Y.dp)
        ) {
            AnimatedLogoIcon(
                alpha = controller.logoAlpha.value,
                offsetY = controller.logoOffsetY.value
            )

            Spacer(modifier = Modifier.height(12.dp))

            AnimatedSplashText(
                text = stringResource(R.string.app_title),
                alpha = controller.textAlpha.value,
                offsetY = controller.textOffsetY.value
            )
        }
    }
}

@Composable
fun AnimatedLogoIcon(
    alpha: Float,
    offsetY: Float,
    modifier: Modifier = Modifier
) {
    var atEnd by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { atEnd = true }

    Image(
        modifier = modifier
            .size(width = 200.dp, height = 160.dp)
            .graphicsLayer {
                this.alpha = alpha
                translationY = offsetY
            },
        painter = rememberAnimatedVectorPainter(
            animatedImageVector = AnimatedImageVector
                .animatedVectorResource(R.drawable.splash_logo_animated),
            atEnd = atEnd
        ),
        contentDescription = stringResource(R.string.app_title)
    )
}

@Composable
fun AnimatedSplashText(
    text: String,
    alpha: Float,
    offsetY: Float,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    color: Color = MaterialTheme.colorScheme.onBackground,
) {
    Text(
        text = text,
        style = style,
        color = color,
        modifier = modifier.graphicsLayer {
            this.alpha = alpha
            translationY = offsetY
        }
    )
}