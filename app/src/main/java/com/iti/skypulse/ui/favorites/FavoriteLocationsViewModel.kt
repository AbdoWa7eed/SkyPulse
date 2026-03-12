package com.iti.skypulse.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.core.extensions.toStateFlow
import com.iti.skypulse.core.utils.TempUnit
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
    settingsRepository: SettingsRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow<FavoriteLocationsState>(FavoriteLocationsState.Loading)
    val state: StateFlow<FavoriteLocationsState> = _state.asStateFlow()

    val tempUnit = settingsRepository.tempUnit
        .toStateFlow(viewModelScope, TempUnit.CELSIUS)

    private val _events = MutableSharedFlow<FavoriteLocationsEvent>()
    val events = _events.asSharedFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _state.value = FavoriteLocationsState.Loading

            weatherRepository.getFavorites()
                .take(1)
                .collect { items ->
                    items.forEach { weather ->
                        launch {
                            weatherRepository.refreshFavorite(weather.latitude, weather.longitude)
                        }
                    }
                }

            weatherRepository.getFavorites().collect { items ->
                _state.value = if (items.isEmpty()) FavoriteLocationsState.Empty
                else FavoriteLocationsState.Success(
                    items.map { weather ->
                        FavoriteLocationItem(
                            weather = weather
                        )
                    }
                )
            }
        }
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