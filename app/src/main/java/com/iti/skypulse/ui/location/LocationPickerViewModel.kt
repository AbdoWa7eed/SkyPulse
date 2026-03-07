package com.iti.skypulse.ui.location

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.skypulse.data.local.prefs.AppPreferences
import com.iti.skypulse.data.local.location.LocationHelper
import com.iti.skypulse.data.model.LocationProvider
import com.iti.skypulse.data.model.SavedLocation
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class LocationPickerEvent {
    data object ShowLocationDisabledDialog : LocationPickerEvent()
    data class ShowSnackBarError(val message: String) : LocationPickerEvent()

    data object ProceedToHome : LocationPickerEvent()
}

class LocationPickerViewModel(
    private val locationHelper: LocationHelper,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _locationState = MutableStateFlow<LocationState>(
        LocationState.NotSet)
    val locationState: StateFlow<LocationState> = _locationState

    private val _events = Channel<LocationPickerEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun fetchGpsLocation() {
        viewModelScope.launch {
            _locationState.value = LocationState.Loading
            locationHelper.getLocation()
                .onSuccess { location -> handleLocationResult(location) }
                .onFailure { handleLocationError(it.message ?: "TIME OUT") }
        }
    }

    private suspend fun handleLocationResult(location: Location?) {
        if (location == null) {
            _events.send(LocationPickerEvent.ShowLocationDisabledDialog)
            _locationState.value = LocationState.NotSet
            return
        }
        val address = locationHelper.getAddressFromLocation(location.latitude, location.longitude)
        _locationState.value = LocationState.Set(
            SavedLocation(
                lat = location.latitude,
                lng = location.longitude,
                provider = LocationProvider.GPS,
                address = address
            )
        )
    }

    private suspend fun handleLocationError(message: String) {
        _events.send(LocationPickerEvent.ShowSnackBarError(message))
        _locationState.value = LocationState.NotSet
    }

    fun setMapLocation(lat: Double, lng: Double) {
        viewModelScope.launch {
            val address = locationHelper.getAddressFromLocation(lat, lng)
            val saved = SavedLocation(
                lat = lat,
                lng = lng,
                provider = LocationProvider.MAP,
                address = address
            )
            _locationState.value = LocationState.Set(saved)
        }
    }

    fun confirmLocation() {
        val current = _locationState.value
        if (current is LocationState.Set) {
            viewModelScope.launch {
                appPreferences.saveLocation(current.location)
                _events.send(LocationPickerEvent.ProceedToHome)
            }
        }
    }

    fun resetState() {
        _locationState.value = LocationState.NotSet
    }
}