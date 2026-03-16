package com.iti.skypulse.ui.home

import com.iti.skypulse.core.error.AppException
import com.iti.skypulse.data.local.location.LocationHelper
import com.iti.skypulse.data.model.location.LocationProvider
import com.iti.skypulse.data.model.location.SavedLocation
import com.iti.skypulse.data.model.weather.DailyForecastModel
import com.iti.skypulse.data.model.weather.ForecastModel
import com.iti.skypulse.data.model.weather.WeatherModel
import com.iti.skypulse.data.repository.weather.WeatherRepository
import com.iti.skypulse.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private val weatherRepository: WeatherRepository = mockk()
    private val locationHelper: LocationHelper = mockk()

    @Test
    fun givenNoEmissions_whenInit_thenLoadingState() {
        every { locationHelper.currentLocation } returns emptyFlow()

        val viewModel = HomeViewModel(weatherRepository, locationHelper)

        assertTrue(viewModel.uiState.value is HomeUiState.Loading)
    }

    @Test
    fun givenNullLocation_whenInit_thenErrorState() = runTest {
        every { locationHelper.currentLocation } returns flowOf(null)

        val viewModel = HomeViewModel(weatherRepository, locationHelper)

        assertTrue(viewModel.uiState.value is HomeUiState.Error)
    }

    @Test
    fun givenValidLocation_whenWeatherSuccess_thenSuccessState() = runTest {
        every { locationHelper.currentLocation } returns flowOf(fakeLocation())
        coEvery { locationHelper.getAddressFromLocation(any(), any()) } returns "Cairo"
        coEvery { weatherRepository.getCurrentWeather(any(), any()) } returns Result.success(fakeWeatherModel())
        coEvery { weatherRepository.getFiveDayForecast(any(), any()) } returns Result.success(fakeForecastModel())

        val viewModel = HomeViewModel(weatherRepository, locationHelper)

        assertTrue(viewModel.uiState.value is HomeUiState.Success)
    }

    @Test
    fun givenValidLocation_whenWeatherFails_thenErrorState() = runTest {
        every { locationHelper.currentLocation } returns flowOf(fakeLocation())
        coEvery { locationHelper.getAddressFromLocation(any(), any()) } returns null
        coEvery { weatherRepository.getCurrentWeather(any(), any()) } returns
                Result.failure(AppException.NoInternetException())
        coEvery { weatherRepository.getFiveDayForecast(any(), any()) } returns
                Result.failure(AppException.NoInternetException())

        val viewModel = HomeViewModel(weatherRepository, locationHelper)

        assertTrue(viewModel.uiState.value is HomeUiState.Error)
    }

    private fun fakeLocation() = SavedLocation(
        lat = 30.0,
        lng = 31.0,
        provider = LocationProvider.GPS,
        address = "Cairo"
    )

    private fun fakeWeatherModel() = WeatherModel(
        conditionCode = 800, temperature = 30.0, feelsLikeTemperature = 32.0,
        minimumTemperature = 25.0, maximumTemperature = 35.0, weatherDescription = "clear sky",
        weatherIconCode = "01d", windSpeed = 5.0, windDirectionDegrees = 180,
        humidityPercentage = 40, visibilityInMeters = 10000, atmosphericPressure = 1013,
        cityName = "Cairo", latitude = 30.0, longitude = 31.0, countryCode = "EG"
    )

    private fun fakeForecastModel() = ForecastModel(
        cityName = "Cairo", latitude = 30.0, longitude = 31.0, countryCode = "EG",
        dailyForecasts = listOf(
            DailyForecastModel(
                dayName = "Monday", date = "2024-01-01", highTemperature = 35.0,
                lowTemperature = 25.0, weatherDescription = "clear sky",
                weatherIconCode = "01d", hourlyForecasts = emptyList()
            )
        )
    )
}