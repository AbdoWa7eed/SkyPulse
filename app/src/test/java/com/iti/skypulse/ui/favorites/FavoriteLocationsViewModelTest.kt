package com.iti.skypulse.ui.favorites

import app.cash.turbine.test
import com.iti.skypulse.data.model.weather.FavoriteWeather
import com.iti.skypulse.data.model.weather.ForecastModel
import com.iti.skypulse.data.model.weather.WeatherModel
import com.iti.skypulse.data.repository.weather.WeatherRepository
import com.iti.skypulse.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.Runs
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteLocationsViewModelTest {

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private val weatherRepository: WeatherRepository = mockk()

    @Test
    fun givenEmptyRepository_whenInit_thenEmptyState() = runTest {
        every { weatherRepository.getFavorites() } returns flowOf(emptyList())

        val viewModel = FavoriteLocationsViewModel(weatherRepository)

        assertEquals(FavoriteLocationsState.Empty, viewModel.state.value)
    }

    @Test
    fun givenFavorites_whenInit_thenSuccessState() = runTest {
        every { weatherRepository.getFavorites() } returns flowOf(listOf(fakeFavoriteWeather()))

        val viewModel = FavoriteLocationsViewModel(weatherRepository)

        assertTrue(viewModel.state.value is FavoriteLocationsState.Success)
    }

    @Test
    fun givenItem_whenOnItemClick_thenSelectedItemUpdated() = runTest {
        every { weatherRepository.getFavorites() } returns flowOf(emptyList())
        val viewModel = FavoriteLocationsViewModel(weatherRepository)
        val item = fakeFavoriteLocationItem()

        viewModel.onItemClick(item)

        assertEquals(item, viewModel.selectedItem.value)
    }

    @Test
    fun givenFavoriteItem_whenRemoveFavoriteItem_thenShowUndoSnackbarEventEmitted() = runTest {
        every { weatherRepository.getFavorites() } returns flowOf(emptyList())
        coEvery { weatherRepository.removeFavorite(any(), any()) } just Runs
        val viewModel = FavoriteLocationsViewModel(weatherRepository)
        val item = fakeFavoriteLocationItem()

        viewModel.events.test {
            viewModel.removeFavoriteItem(item)
            assertTrue(awaitItem() is FavoriteLocationsEvent.ShowUndoSnackbar)
        }
    }

    private fun fakeFavoriteWeather() = FavoriteWeather(
        weather = fakeWeatherModel(),
        forecast = fakeForecastModel()
    )

    private fun fakeFavoriteLocationItem() = FavoriteLocationItem(
        weather = fakeWeatherModel(),
        forecast = fakeForecastModel()
    )

    private fun fakeWeatherModel() = WeatherModel(
        conditionCode = 800, temperature = 30.0, feelsLikeTemperature = 32.0,
        minimumTemperature = 25.0, maximumTemperature = 35.0, weatherDescription = "clear sky",
        weatherIconCode = "01d", windSpeed = 5.0, windDirectionDegrees = 180,
        humidityPercentage = 40, visibilityInMeters = 10000, atmosphericPressure = 1013,
        cityName = "Cairo", latitude = 30.0, longitude = 31.0, countryCode = "EG"
    )

    private fun fakeForecastModel() = ForecastModel(
        cityName = "Cairo", latitude = 30.0, longitude = 31.0,
        countryCode = "EG", dailyForecasts = emptyList()
    )
}