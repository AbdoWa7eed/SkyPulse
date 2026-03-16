package com.iti.skypulse.data.local.room.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iti.skypulse.data.local.room.AppDatabase
import com.iti.skypulse.data.local.room.entity.WeatherAlertEntity
import com.iti.skypulse.data.model.alert.AlertNotificationType
import com.iti.skypulse.data.model.alert.WeatherAlertType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherAlertDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: WeatherAlertDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.weatherAlertDao()
    }

    @After
    fun teardown() = db.close()

    @Test
    fun givenAlert_whenInsertAndGetById_thenReturnsCorrectAlert() = runTest {
        val alert = buildAlertEntity("1")

        dao.insert(alert)
        val result = dao.getById("1")

        assertEquals(alert, result)
    }

    @Test
    fun givenNoAlerts_whenGetById_thenReturnsNull() = runTest {
        val result = dao.getById("999")

        assertNull(result)
    }

    @Test
    fun givenInsertedAlert_whenDelete_thenGetByIdReturnsNull() = runTest {
        dao.insert(buildAlertEntity("1"))

        dao.delete("1")
        val result = dao.getById("1")

        assertNull(result)
    }

    @Test
    fun givenEnabledAlert_whenSetEnabledFalse_thenFlagUpdated() = runTest {
        dao.insert(buildAlertEntity("1", isEnabled = true))

        dao.setEnabled("1", false)
        val result = dao.getById("1")

        assertNotNull(result)
        assertFalse(result!!.isEnabled)
    }

    @Test
    fun givenDisabledAlert_whenSetEnabledTrue_thenFlagUpdated() = runTest {
        dao.insert(buildAlertEntity("1", isEnabled = false))

        dao.setEnabled("1", true)
        val result = dao.getById("1")

        assertNotNull(result)
        assertTrue(result!!.isEnabled)
    }

    @Test
    fun givenMultipleAlerts_whenGetAll_thenOrderedByScheduledTime() = runTest {
        dao.insert(buildAlertEntity("2", scheduledTime = 2000L))
        dao.insert(buildAlertEntity("1", scheduledTime = 1000L))
        dao.insert(buildAlertEntity("3", scheduledTime = 3000L))

        val result = dao.getAll().first()

        assertEquals(listOf("1", "2", "3"), result.map { it.id })
    }

    @Test
    fun givenExistingAlert_whenInsertSameId_thenReplacesOldAlert() = runTest {
        dao.insert(buildAlertEntity("1", type = WeatherAlertType.RAIN))

        dao.insert(buildAlertEntity("1", type = WeatherAlertType.SNOW))
        val result = dao.getById("1")

        assertEquals(WeatherAlertType.SNOW, result?.type)
    }

    @Test
    fun givenExistingAlert_whenDeleteNonExistingId_thenOriginalAlertUnaffected() = runTest {
        dao.insert(buildAlertEntity("1"))

        dao.delete("999")
        val result = dao.getById("1")

        assertNotNull(result)
    }

    private fun buildAlertEntity(
        id: String,
        type: WeatherAlertType = WeatherAlertType.RAIN,
        isEnabled: Boolean = true,
        scheduledTime: Long = System.currentTimeMillis() + 60_000L
    ) = WeatherAlertEntity(
        id = id,
        type = type,
        notificationType = AlertNotificationType.NOTIFICATION,
        isEnabled = isEnabled,
        scheduledTime = scheduledTime,
        endTime = scheduledTime + 3600_000L
    )
}