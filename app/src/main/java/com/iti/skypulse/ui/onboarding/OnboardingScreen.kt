package com.iti.skypulse.ui.onboarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iti.skypulse.R
import com.iti.skypulse.ui.components.ElevatedPrimaryButton
import com.iti.skypulse.ui.components.PrimaryTextButton
import com.iti.skypulse.ui.onboarding.components.DotsIndicator
import com.iti.skypulse.ui.onboarding.components.OnboardingPage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onFinished: () -> Unit = {}
) {
    val viewModel: OnboardingViewModel = viewModel(factory = OnboardingViewModelFactory())

    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collectLatest {
            onFinished()
        }
    }

    Scaffold { innerPadding ->
        OnboardingContent(
            modifier = modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}

@Composable
private fun OnboardingContent(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel
) {
    val currentPage by viewModel.currentPage.collectAsState()
    val showSkip by viewModel.showSkip.collectAsState()
    val pages = viewModel.pages
    val skipAlpha by animateFloatAsState(targetValue = if (showSkip) 1f else 0f)


    val pagerState = rememberPagerState(
        initialPage = currentPage,
        pageCount = { pages.size }
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                if (currentPage != page) viewModel.updateCurrentPage(page)
            }
    }

    LaunchedEffect(currentPage) {
        if (pagerState.currentPage != currentPage)
            pagerState.animateScrollToPage(currentPage)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            PrimaryTextButton(
                enabled = showSkip,
                text = stringResource(R.string.skip),
                onClick = { viewModel.finishOnboarding() },
                modifier = Modifier.alpha(skipAlpha)
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { pageIndex ->
            OnboardingPage(onboardingModel = pages[pageIndex])
        }

        DotsIndicator(
            totalDots = pages.size,
            selectedIndex = currentPage,
            modifier = Modifier.padding(vertical = 16.dp)
        )


        ElevatedPrimaryButton(
            text = if (currentPage == pages.lastIndex)
                stringResource(R.string.get_started)
            else
                stringResource(R.string.next),
            onClick = { viewModel.onNext() }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
