package com.iti.skypulse.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.iti.skypulse.core.utils.TempUnit
import com.iti.skypulse.data.local.location.LocationHelper
import com.iti.skypulse.data.model.GeoPlace
import com.iti.skypulse.data.model.LocationProvider
import com.iti.skypulse.data.model.SavedLocation
import com.iti.skypulse.data.repository.WeatherRepository
import com.iti.skypulse.data.repository.settings.SettingsRepository
import com.iti.skypulse.ui.navigation.MapSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(FlowPreview::class)
class MapViewModel(
    private val source: MapSource,
    private val weatherRepository: WeatherRepository,
    private val locationHelper: LocationHelper,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _selectionState = MutableStateFlow<MapSelectionState>(MapSelectionState.Idle)
    val selectionState: StateFlow<MapSelectionState> = _selectionState.asStateFlow()

    private val _searchResults = MutableStateFlow<List<GeoPlace>>(emptyList())
    val searchResults: StateFlow<List<GeoPlace>> = _searchResults.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _events = MutableSharedFlow<MapEvent>(replay = 1)
    val events = _events.asSharedFlow()

    private var tempUnit: TempUnit = TempUnit.CELSIUS

    init {
        viewModelScope.launch(Dispatchers.IO) {
            tempUnit = settingsRepository.tempUnit.first()
            val saved = settingsRepository.savedLocation.first()
            val position = saved?.let { LatLng(it.lat, it.lng) } ?: LatLng(0.0, 0.0)
            val zoom = if (saved != null) 12f else 2f
            withContext(Dispatchers.Main) {
                _events.emit(MapEvent.MoveCameraTo(position, zoom))
                saved?.let {
                    _selectionState.value = MapSelectionState.AddressResolved(it)
                    getCurrentWeather(it)

                }
            }

        }
        observeSearchQuery()
    }
    private fun observeSearchQuery() {
        _searchQuery
            .debounce(400)
            .distinctUntilChanged()
            .filter { it.length >= 2 }
            .onEach { query ->
                weatherRepository.searchPlaces(query)
                    .onSuccess { _searchResults.value = it }
                    .onFailure { _searchResults.value = emptyList() }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) _searchResults.value = emptyList()
    }

    fun onPlaceSelected(place: GeoPlace) {
        _searchResults.value = emptyList()
        _searchQuery.value = place.displayName
        viewModelScope.launch {
            _events.emit(MapEvent.MoveCameraTo(LatLng(place.latitude, place.longitude), zoom = 12f))
        }
        onMapClick(place.latitude, place.longitude)
    }

    fun onMapClick(lat: Double, lng: Double) {
        viewModelScope.launch {
            _selectionState.value = MapSelectionState.ResolvingAddress

            val address = locationHelper.getAddressFromLocation(lat, lng)
            val location = SavedLocation(
                lat = lat,
                lng = lng,
                provider = LocationProvider.MAP,
                address = address
            )

            _selectionState.value = MapSelectionState.AddressResolved(location)

            getCurrentWeather(location)

        }
    }

    suspend fun getCurrentWeather(location: SavedLocation) {
        weatherRepository.getCurrentWeather(location.lat, location.lng)
            .onSuccess { weather ->
                _selectionState.value = MapSelectionState.WeatherLoaded(
                    location = location,
                    weather = weather,
                    tempUnit = tempUnit
                )
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
                    weatherRepository.addFavorite(location.lat, location.lng)
                        .onSuccess { _events.emit(MapEvent.NavigateBack) }
                        .onFailure { _events.emit(MapEvent.ShowError(it)) }
                }
            }
        }
    }

    private fun confirmedLocation(): SavedLocation? =
        when (val s = _selectionState.value) {
            is MapSelectionState.AddressResolved -> s.location
            is MapSelectionState.WeatherLoaded   -> s.location
            else                                 -> null
        }
}