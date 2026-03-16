package com.iti.skypulse.ui.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.ui.onboarding.OnboardingPageData
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.ui.theme.SkyPulseTheme


@Composable
fun OnboardingPage(
    modifier: Modifier = Modifier,

    onboardingModel: OnboardingPageData
) {

    Column(
        modifier = modifier.fillMaxSize().padding(20.dp),
    ) {

        Image(
            painter = painterResource(onboardingModel.imageRes),
            contentDescription = onboardingModel.imageRes.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            alignment = Alignment.Center
        )

        Text(
            text = stringResource(onboardingModel.title),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            textAlign = TextAlign.Center,
            style = AppTypography.semiBold24,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = stringResource(onboardingModel.subtitle),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = AppTypography.regular14,
            color = MaterialTheme.colorScheme.onSecondary
        )

    }
}


@Preview
@Composable
fun OnboardingPagePreview() {
    SkyPulseTheme {
        Scaffold {
            innerPadding ->
            OnboardingPage(
                modifier = Modifier.padding(innerPadding),
                OnboardingPageData.FirstPage
            )

        }
    }
}