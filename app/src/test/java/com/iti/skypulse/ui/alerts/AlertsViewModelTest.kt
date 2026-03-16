    package com.iti.skypulse.ui.alerts

    import app.cash.turbine.test
    import com.iti.skypulse.core.error.AppException
    import com.iti.skypulse.data.model.alert.AlertNotificationType
    import com.iti.skypulse.data.model.alert.WeatherAlertType
    import com.iti.skypulse.data.repository.alerts.WeatherAlertRepository
    import com.iti.skypulse.util.MainDispatcherRule
    import io.mockk.coEvery
    import io.mockk.coVerify
    import io.mockk.mockk
    import kotlinx.coroutines.ExperimentalCoroutinesApi
    import kotlinx.coroutines.test.runTest
    import org.junit.Assert.*
    import org.junit.Before
    import org.junit.Rule
    import org.junit.Test

    @OptIn(ExperimentalCoroutinesApi::class)
    class AlertsViewModelTest {

        @get:Rule
        val coroutineRule = MainDispatcherRule()

        private val repository: WeatherAlertRepository = mockk(relaxed = true)
        private lateinit var viewModel: AlertsViewModel

        @Before
        fun setup() {
            viewModel = AlertsViewModel(repository)
        }

        @Test
        fun givenClosedSheet_whenOpenAddSheet_thenShowAddSheetIsTrue() {
            viewModel.openAddSheet()

            assertTrue(viewModel.showAddSheet.value)
        }

        @Test
        fun givenAlertId_whenDeleteAlert_thenRepositoryDeleteCalled() = runTest {
            viewModel.deleteAlert("1")

            coVerify { repository.deleteAlert("1") }
        }

        @Test
        fun givenExpiredAlert_whenToggleAlert_thenAlertTimeExpiredEventEmitted() = runTest {
            coEvery { repository.toggleAlert("1", true) } returns
                    Result.failure(AppException.AlertExpiredException())

            viewModel.events.test {
                viewModel.toggleAlert("1", true)
                assertTrue(awaitItem() is AlertsEvent.AlertTimeExpired)
            }
        }

        @Test
        fun givenAlarmTypeForm_whenAddAlert_thenRequestAlarmPermissionEventEmitted() = runTest {
            viewModel.events.test {
                viewModel.addAlert(buildForm())
                assertTrue(awaitItem() is AlertsEvent.RequestAlarmPermission)
            }
        }

        private fun buildForm() = AlertFormState(
            type = WeatherAlertType.RAIN,
            notificationType = AlertNotificationType.ALARM,
            dateMillis = System.currentTimeMillis() + 3600_000L,
            hour = 10, minute = 0,
            endDateMillis = System.currentTimeMillis() + 7200_000L,
            endHour = 11, endMinute = 0
        )
    }