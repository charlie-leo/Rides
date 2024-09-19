package com.assignment.rides.data.repositoryImpl

import com.assignment.rides.data.model.VehicleModel
import com.assignment.rides.data.retrofit.RetrofitInterface
import com.assignment.rides.domain.repository.VehicleRepository
import com.assignment.rides.presentation.util.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */
class VehicleRepositoryImpl @Inject constructor(
    private val retrofitInterface: RetrofitInterface
) : VehicleRepository {

    override suspend fun fetchVehicle(count: Int, callBack: (ApiResult<List<VehicleModel>>) -> Unit) {
            withContext(Dispatchers.IO){
                val result = retrofitInterface.fetchVehicleListCall(count).execute()
                val response: ApiResult<List<VehicleModel>>

                when (result.code()){
                    200 -> {
                        response = ApiResult.Success(
                            code = result.code().toString(),
                            message = "Fetch Success",
                            body = result.body()
                        )
                    }
                    400 -> {
                        response = ApiResult.Error(
                            code = result.code().toString(),
                            message = result.message(),
                            body = null
                        )
                    }
                    500 -> {
                        response = ApiResult.InternalServerError()
                    }
                    else -> {
                        response = ApiResult.NoNetwork()
                    }
                }
                callBack(response)
            }
    }
}