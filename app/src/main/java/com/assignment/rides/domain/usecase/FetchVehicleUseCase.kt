package com.assignment.rides.domain.usecase

import com.assignment.rides.data.model.VehicleModel
import com.assignment.rides.domain.repository.VehicleRepository
import com.assignment.rides.presentation.util.ApiResult
import javax.inject.Inject

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */

class FetchVehicleUseCase @Inject constructor(
    private val vehicleRepository: VehicleRepository
) {

    suspend fun execute(count : Int, callBack : (ApiResult<List<VehicleModel>>) -> Unit) {
        vehicleRepository.fetchVehicle(count,callBack)
    }

}