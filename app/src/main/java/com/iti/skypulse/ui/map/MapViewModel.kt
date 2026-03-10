package com.iti.skypulse.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.data.local.location.LocationHelper
import com.iti.skypulse.data.model.LocationProvider
import com.iti.skypulse.data.model.SavedLocation
import com.iti.skypulse.data.repository.WeatherRepository
import com.iti.skypulse.data.repository.settings.SettingsRepository
import com.iti.skypulse.ui.navigation.MapSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MapViewModel(
    private val source: MapSource,
    private val weatherRepository: WeatherRepository,
    private val locationHelper: LocationHelper,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MapSelectionState>(MapSelectionState.Idle)
    val uiState: StateFlow<MapSelectionState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<MapEvent>()
    val events = _events.asSharedFlow()

    private lateinit var tempUnit: TempUnit

    var initialPosition: LatLng = LatLng(0.0, 0.0)
        private set
    val initialZoom: Float
        get() = if (initialPosition == LatLng(0.0, 0.0)) 2f else 12f

    init {
        viewModelScope.launch {
            tempUnit = settingsRepository.tempUnit.first()
            val saved = settingsRepository.savedLocation.first()
            saved?.let { initialPosition = LatLng(it.lat, it.lng) }
        }
    }

    fun onMapClick(lat: Double, lng: Double) {
        viewModelScope.launch {
            _uiState.value = MapSelectionState.ResolvingAddress

            val address = locationHelper.getAddressFromLocation(lat, lng)
            val location = SavedLocation(
                lat = lat,
                lng = lng,
                provider = LocationProvider.MAP,
                address = address
            )

            _uiState.value = MapSelectionState.AddressResolved(location)

            weatherRepository.getCurrentWeather(lat, lng)
                .onSuccess { weather ->
                    _uiState.value = MapSelectionState.WeatherLoaded(
                        location = location,
                        weather = weather,
                        tempUnit = tempUnit
                    )
                }
        }
    }

    fun onConfirm() {
        val location = confirmedLocation() ?: return
        viewModelScope.launch {
            when (source) {
                MapSource.ONBOARDING, MapSource.UPDATE_LOCATION -> {
                    settingsRepository.saveLocation(location)
                    _events.emit(MapEvent.NavigateToMain)
                }
                MapSource.ADD_FAVORITE -> {
                    // TODO: Implement ADD FAVORITE LOGIC
                    _events.emit(MapEvent.NavigateBack)
                }
            }
        }
    }

    private fun confirmedLocation(): SavedLocation? =
        when (val s = _uiState.value) {
            is MapSelectionState.AddressResolved -> s.location
            is MapSelectionState.WeatherLoaded   -> s.location
            else                                 -> null
        }
}