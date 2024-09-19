package com.assignment.rides

import com.assignment.rides.presentation.ui.fragments.CarbonEmissionSheetFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Charles Raj I on 19/09/24
 * @project Rides
 * @author Charles Raj
 */

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner::class)
class VehicleDetailsTest {


//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

    }

    @Test
    fun `Calculate Emission For Below 5000 KM`() {
        val input = 4500
        val fragment = CarbonEmissionSheetFragment(input)
        val calculator = fragment.calculateEmissions(input)
        assert(calculator == input.toDouble())
    }

    @Test
    fun `Calculate Emission For Above 5000 KM`() {
        val input = 8600
        val fragment = CarbonEmissionSheetFragment(input)
        val calculator = fragment.calculateEmissions(input)
        assert(calculator == 10400.0)
    }

}