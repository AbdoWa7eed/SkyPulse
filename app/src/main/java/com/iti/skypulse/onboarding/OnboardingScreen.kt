package com.iti.skypulse.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.R
import com.iti.skypulse.common.ElevatedPrimaryButton
import com.iti.skypulse.common.PrimaryTextButton
import com.iti.skypulse.onboarding.model.OnboardingModel
import com.iti.skypulse.ui.theme.SkyPulseTheme


@Composable
fun OnboardingScreen(modifier: Modifier = Modifier) {

    Scaffold { innerPadding ->
        OnboardingContent(
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
private fun OnboardingContent(modifier: Modifier = Modifier) {

    val pages = listOf(
        OnboardingModel.FirstPage,
        OnboardingModel.SecondPage,
        OnboardingModel.ThirdPage
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

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
                onClick = { /* navigate to home */ }
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
            selectedIndex = pagerState.currentPage,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        ElevatedPrimaryButton(
            text = if (pagerState.currentPage == pages.lastIndex)
                stringResource(R.string.get_started)
            else
                stringResource(R.string.next),
            onClick = { /* next or finish */ }
        )

        Spacer(modifier = Modifier.padding(vertical = 16.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    SkyPulseTheme {
        Scaffold {
                innerPadding ->
            OnboardingScreen(
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}