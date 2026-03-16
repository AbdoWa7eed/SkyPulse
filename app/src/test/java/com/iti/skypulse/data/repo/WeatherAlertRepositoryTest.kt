package com.iti.skypulse.data.repo

import com.iti.skypulse.core.error.AppException
import com.iti.skypulse.core.scheduler.WeatherAlertScheduler
import com.iti.skypulse.data.local.datasource.alerts.WeatherAlertLocalDataSource
import com.iti.skypulse.data.local.room.entity.WeatherAlertEntity
import com.iti.skypulse.data.model.alert.AlertNotificationType
import com.iti.skypulse.data.model.alert.WeatherAlert
import com.iti.skypulse.data.model.alert.WeatherAlertType
import com.iti.skypulse.data.repository.alerts.WeatherAlertRepository
import com.iti.skypulse.data.repository.alerts.WeatherAlertRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherAlertRepositoryTest {

    private val localDataSource: WeatherAlertLocalDataSource = mockk(relaxed = true)
    private val scheduler: WeatherAlertScheduler = mockk(relaxed = true)
    private lateinit var repository: WeatherAlertRepository

    @Before
    fun setup() {
        repository = WeatherAlertRepositoryImpl(localDataSource, scheduler)
    }

    @Test
    fun givenAlertsInDataSource_whenGetAlerts_thenReturnsMappedModels() = runTest {
        every { localDataSource.getAll() } returns flowOf(listOf(buildAlertEntity("1")))

        val result = repository.getAlerts().first()

        Assert.assertEquals(1, result.size)
        Assert.assertEquals("1", result.first().id)
        Assert.assertEquals(WeatherAlertType.RAIN, result.first().type)
    }

    @Test
    fun givenAlert_whenAddAlert_thenInsertsAndSchedules() = runTest {
        val alert = buildAlert()

        repository.addAlert(alert)

        coVerify { localDataSource.insert(any()) }
        verify { scheduler.schedule(alert) }
    }

    @Test
    fun givenAlertId_whenDeleteAlert_thenDeletesAndCancelsScheduler() = runTest {
        repository.deleteAlert("1")

        coVerify { localDataSource.delete("1") }
        verify { scheduler.cancel("1") }
    }

    @Test
    fun givenAlert_whenToggleAlertFalse_thenCancelsScheduler() = runTest {
        coEvery { localDataSource.getById("1") } returns buildAlertEntity("1")

        repository.toggleAlert("1", false)

        verify { scheduler.cancel("1") }
    }

    @Test
    fun givenFutureAlert_whenToggleAlertTrue_thenReschedulesAlert() = runTest {
        coEvery { localDataSource.getById("1") } returns buildAlertEntity(
            "1",
            scheduledTime = System.currentTimeMillis() + 3600_000L,
            endTime = System.currentTimeMillis() + 7200_000L
        )

        repository.toggleAlert("1", true)

        verify { scheduler.schedule(any()) }
    }

    @Test
    fun givenExpiredAlert_whenToggleAlertTrue_thenReturnsFailure() = runTest {
        coEvery { localDataSource.getById("1") } returns buildAlertEntity(
            "1",
            scheduledTime = System.currentTimeMillis() - 7200_000L,
            endTime = System.currentTimeMillis() - 3600_000L
        )

        val result = repository.toggleAlert("1", true)

        Assert.assertTrue(result.isFailure)
        Assert.assertTrue(result.exceptionOrNull() is AppException.AlertExpiredException)
    }

    @Test
    fun givenExistingAlert_whenGetAlertById_thenReturnsMappedModel() = runTest {
        coEvery { localDataSource.getById("1") } returns buildAlertEntity("1")

        val result = repository.getAlertById("1")

        Assert.assertNotNull(result)
        Assert.assertEquals("1", result?.id)
    }

    @Test
    fun givenNoAlert_whenGetAlertById_thenReturnsNull() = runTest {
        coEvery { localDataSource.getById("999") } returns null

        val result = repository.getAlertById("999")

        Assert.assertNull(result)
    }

    private fun buildAlertEntity(
        id: String,
        scheduledTime: Long = System.currentTimeMillis() + 60_000L,
        endTime: Long = System.currentTimeMillis() + 3600_000L
    ) = WeatherAlertEntity(
        id = id,
        type = WeatherAlertType.RAIN,
        notificationType = AlertNotificationType.NOTIFICATION,
        isEnabled = true,
        scheduledTime = scheduledTime,
        endTime = endTime
    )

    private fun buildAlert() = WeatherAlert(
        id = "1",
        type = WeatherAlertType.RAIN,
        notificationType = AlertNotificationType.NOTIFICATION,
        isEnabled = true,
        scheduledTime = System.currentTimeMillis() + 60_000L,
        endTime = System.currentTimeMillis() + 3600_000L
    )
}