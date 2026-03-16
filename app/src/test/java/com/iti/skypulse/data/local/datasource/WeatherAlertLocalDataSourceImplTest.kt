package com.iti.skypulse.data.local.datasource

import com.iti.skypulse.data.local.datasource.alerts.WeatherAlertLocalDataSource
import com.iti.skypulse.data.local.datasource.alerts.WeatherAlertLocalDataSourceImpl
import com.iti.skypulse.data.local.room.dao.WeatherAlertDao
import com.iti.skypulse.data.local.room.entity.WeatherAlertEntity
import com.iti.skypulse.data.model.alert.AlertNotificationType
import com.iti.skypulse.data.model.alert.WeatherAlertType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WeatherAlertLocalDataSourceTest {

    private val dao: WeatherAlertDao = mockk(relaxed = true)
    private lateinit var dataSource: WeatherAlertLocalDataSource

    @Before
    fun setup() {
        dataSource = WeatherAlertLocalDataSourceImpl(dao)
    }

    @Test
    fun givenAlertsInDao_whenGetAll_thenReturnsMappedList() = runTest {
        val entities = listOf(buildAlertEntity("1"), buildAlertEntity("2"))
        every { dao.getAll() } returns flowOf(entities)

        val result = dataSource.getAll().first()

        assertEquals(entities, result)
    }

    @Test
    fun givenAlert_whenInsert_thenDaoInsertCalled() = runTest {
        val entity = buildAlertEntity("1")

        dataSource.insert(entity)

        coVerify { dao.insert(entity) }
    }

    @Test
    fun givenAlertId_whenDelete_thenDaoDeleteCalledWithCorrectId() = runTest {
        dataSource.delete("1")

        coVerify { dao.delete("1") }
    }

    @Test
    fun givenAlertId_whenSetEnabledTrue_thenDaoSetEnabledCalledCorrectly() = runTest {
        dataSource.setEnabled("1", true)

        coVerify { dao.setEnabled("1", true) }
    }

    @Test
    fun givenAlertId_whenSetEnabledFalse_thenDaoSetEnabledCalledCorrectly() = runTest {
        dataSource.setEnabled("1", false)

        coVerify { dao.setEnabled("1", false) }
    }

    @Test
    fun givenExistingAlert_whenGetById_thenReturnsEntity() = runTest {
        val entity = buildAlertEntity("1")
        coEvery { dao.getById("1") } returns entity

        val result = dataSource.getById("1")

        assertEquals(entity, result)
    }

    @Test
    fun givenNoAlert_whenGetById_thenReturnsNull() = runTest {
        coEvery { dao.getById("999") } returns null

        val result = dataSource.getById("999")

        assertNull(result)
    }

    @Test
    fun givenEmptyDao_whenGetAll_thenReturnsEmptyList() = runTest {
        every { dao.getAll() } returns flowOf(emptyList())

        val result = dataSource.getAll().first()

        assertTrue(result.isEmpty())
    }

    private fun buildAlertEntity(id: String) = WeatherAlertEntity(
        id = id,
        type = WeatherAlertType.RAIN,
        notificationType = AlertNotificationType.NOTIFICATION,
        isEnabled = true,
        scheduledTime = System.currentTimeMillis() + 60_000L,
        endTime = System.currentTimeMillis() + 3600_000L
    )
}