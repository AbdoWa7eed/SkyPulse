package com.iti.skypulse.ui.favorites

import FavoriteLocationsViewModelFactory
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddLocation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iti.skypulse.R
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.ui.components.ErrorScreen
import com.iti.skypulse.ui.components.PrimaryAppBar
import com.iti.skypulse.ui.favorites.components.EmptyFavoritesState
import com.iti.skypulse.ui.favorites.components.details.FavoriteDetailBottomSheet
import com.iti.skypulse.ui.favorites.components.FavoriteLocationsList
import com.iti.skypulse.ui.favorites.components.FavoriteLocationsShimmer
import com.iti.skypulse.ui.favorites.components.rememberFavoriteSnackbarState
import com.iti.skypulse.ui.theme.LocalAppUnits

@Composable
fun FavoriteLocationsScreen(
    onAddFavorite: () -> Unit,
    viewModel: FavoriteLocationsViewModel = viewModel(factory = FavoriteLocationsViewModelFactory())
) {
    val state by viewModel.state.collectAsState()
    val units = LocalAppUnits.current
    val selectedItem by viewModel.selectedItem.collectAsState()

    val snackbarHostState = rememberFavoriteSnackbarState(
        events = viewModel.events,
        onUndo = viewModel::undo,
    )

    Box(modifier = Modifier.fillMaxSize()) {

        FavoriteLocationsContent(
            state = state,
            tempUnit = units.tempUnit,
            onRemove = viewModel::removeFavoriteItem,
            onClickItem = viewModel::onItemClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        )

        FavoriteFloatingActionButton(
            onAddFavorite = onAddFavorite,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 8.dp, vertical = 16.dp)
        )
    }

    selectedItem?.let { item ->
        FavoriteDetailBottomSheet(
            item = item,
            units = units,
            onDismiss = viewModel::onBottomSheetDismiss
        )
    }
}

@Composable
private fun FavoriteFloatingActionButton(
    onAddFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onAddFavorite,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 12.dp
        )
    ) {
        Icon(
            imageVector = Icons.Rounded.AddLocation,
            contentDescription = stringResource(R.string.add_favorite)
        )
    }
}

@Composable
private fun FavoriteLocationsContent(
    state: FavoriteLocationsState,
    tempUnit: TempUnit,
    onRemove: (FavoriteLocationItem) -> Unit,
    onClickItem: (FavoriteLocationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        PrimaryAppBar(title = stringResource(R.string.favorites))

        when (state) {
            is FavoriteLocationsState.Loading -> FavoriteLocationsShimmer()
            is FavoriteLocationsState.Empty -> EmptyFavoritesState()
            is FavoriteLocationsState.Success -> FavoriteLocationsList(
                items = state.items,
                tempUnit = tempUnit,
                onRemove = onRemove,
                onClickItem = onClickItem
            )
            is FavoriteLocationsState.Error -> ErrorScreen(title = state.message)
        }
    }
}