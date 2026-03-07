package com.iti.skypulse.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.skypulse.ui.theme.AppTypography
import com.iti.skypulse.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar() {
    TopAppBar(
        windowInsets = WindowInsets(0),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Column {
                Text(
                    text = stringResource(R.string.app_title),
                    style = AppTypography.bold20,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Awsim, Giza",
                    style = AppTypography.medium12,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    )
}

@Preview
@Composable
fun HomeAppBarPreview() {
    HomeAppBar()
}