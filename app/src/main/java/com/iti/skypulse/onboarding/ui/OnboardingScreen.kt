package com.iti.skypulse.onboarding.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iti.skypulse.R
import com.iti.skypulse.common.ElevatedPrimaryButton
import com.iti.skypulse.common.PrimaryTextButton
import com.iti.skypulse.onboarding.model.OnboardingModel
import com.iti.skypulse.onboarding.viewmodel.OnboardingViewModel
import com.iti.skypulse.onboarding.viewmodel.OnboardingViewModelFactory
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
    val pages = remember {
        listOf(
            OnboardingModel.FirstPage,
            OnboardingModel.SecondPage,
            OnboardingModel.ThirdPage
        )
    }

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
                text = stringResource(R.string.skip),
                onClick = { viewModel.finishOnboarding() }
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
