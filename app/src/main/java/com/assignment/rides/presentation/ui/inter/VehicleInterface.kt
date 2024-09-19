package com.assignment.rides.presentation.ui.inter

import com.assignment.rides.data.model.VehicleModel

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */
interface VehicleInterface {

    fun selectVehicleItem(vehicleModel: VehicleModel)

    fun onFragmentBack()

}