package com.assignment.rides.data.retrofit

import com.assignment.rides.data.model.VehicleModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */

interface RetrofitInterface {


    @GET("api/vehicle/random_vehicle")
    fun fetchVehicleListCall(@Query("size") size: Int): Call<List<VehicleModel>>

}