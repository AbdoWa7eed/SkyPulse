package com.iti.skypulse.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.iti.skypulse.R

sealed class OnboardingModel(
    @param:DrawableRes val imageRes: Int,
    @param:StringRes val title: Int,
    @param:StringRes val subtitle: Int,
) {

    data object FirstPage : OnboardingModel(
        imageRes = R.drawable.onboarding_image_1,
        title = R.string.onboarding_title_1,
        subtitle = R.string.onboarding_subtitle_1
    )

    data object SecondPage : OnboardingModel(
        imageRes = R.drawable.onboarding_image_2,
        title = R.string.onboarding_title_2,
        subtitle = R.string.onboarding_subtitle_2
    )

    data object ThirdPage : OnboardingModel(
        imageRes = R.drawable.onboarding_image_3,
        title = R.string.onboarding_title_3,
        subtitle = R.string.onboarding_subtitle_3
    )



}