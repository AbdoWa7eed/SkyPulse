package com.iti.skypulse.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.core.extensions.toStateFlow
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.data.model.FavoriteWeather
import com.iti.skypulse.data.repository.WeatherRepository
import com.iti.skypulse.data.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class FavoriteLocationsViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow<FavoriteLocationsState>(FavoriteLocationsState.Loading)
    val state: StateFlow<FavoriteLocationsState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<FavoriteLocationsEvent>()
    val events = _events.asSharedFlow()

    private val _selectedItem = MutableStateFlow<FavoriteLocationItem?>(null)
    val selectedItem: StateFlow<FavoriteLocationItem?> = _selectedItem.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _state.value = FavoriteLocationsState.Loading
            syncFavorites()
            observeFavorites()
        }
    }

    private suspend fun syncFavorites() {
        weatherRepository.getFavorites()
            .take(1)
            .collect { favorites ->
                favorites.forEach { fav ->
                    viewModelScope.launch {
                        weatherRepository.refreshFavorite(
                            fav.weather.latitude,
                            fav.weather.longitude
                        )
                    }
                }
            }
    }

    private suspend fun observeFavorites() {
        weatherRepository.getFavorites().collect { favorites ->
            _state.value = if (favorites.isEmpty()) FavoriteLocationsState.Empty
            else FavoriteLocationsState.Success(favorites.map { it.toUiItem() })
        }
    }

    private fun FavoriteWeather.toUiItem() = FavoriteLocationItem(
        weather = weather,
        forecast = forecast
    )

    fun onItemClick(item: FavoriteLocationItem) {
        _selectedItem.value = item
    }

    fun onBottomSheetDismiss() {
        _selectedItem.value = null
    }

    fun removeFavoriteItem(item: FavoriteLocationItem) {
        viewModelScope.launch {
            weatherRepository.removeFavorite(item.weather.latitude, item.weather.longitude)
            _events.emit(FavoriteLocationsEvent.ShowUndoSnackbar(item))
        }
    }

    fun undo(item: FavoriteLocationItem) {
        viewModelScope.launch {
            weatherRepository.addFavorite(item.weather.latitude, item.weather.longitude)
        }
    }
}