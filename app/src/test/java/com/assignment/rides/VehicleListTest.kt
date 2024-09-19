package com.assignment.rides

import android.text.TextUtils
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.rides.domain.usecase.FetchVehicleUseCase
import com.assignment.rides.presentation.ui.viewmodel.VehicleListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.createTestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mockStatic
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner::class)
class VehicleListTest {

    @Mock
    lateinit var fetchVehicleUseCase: FetchVehicleUseCase
    private lateinit var viewModel: VehicleListViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = VehicleListViewModel(fetchVehicleUseCase)
    }

    @Test
    fun `Check Validate When Input Is Empty`() {
        val result = viewModel.validateCount("")
        assert(!result.first)
        assert(result.second == "Value must not be empty.")
    }

    @Test
    fun `Check Validate When Input Is Greater Then 100`() {
        val result = viewModel.validateCount("102")
        assert(!result.first)
        assert(result.second == "Please Enter value between 1 to 100.")
    }

    @Test
    fun `Check Validate When Input Is Less Then 1`() {
        val result = viewModel.validateCount("0")
        assert(!result.first)
        assert(result.second == "Please Enter value between 1 to 100.")
    }

    @Test
    fun `Check Validate When Input Is Correct`() {
        val result = viewModel.validateCount("67")
        assert(result.first)
        assert(result.second == "")
    }
}
