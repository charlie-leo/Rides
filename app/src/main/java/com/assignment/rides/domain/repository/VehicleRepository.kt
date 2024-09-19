package com.assignment.rides.domain.repository

import com.assignment.rides.data.model.VehicleModel
import com.assignment.rides.presentation.util.ApiResult

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */
interface VehicleRepository {

    suspend fun fetchVehicle(count : Int, callBack : (ApiResult<List<VehicleModel>>) -> Unit)

}