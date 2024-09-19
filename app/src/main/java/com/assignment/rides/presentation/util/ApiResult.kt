package com.assignment.rides.presentation.util

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */
sealed class ApiResult<T> (
    val code : String? = null,
    val message : String? = null,
    val body: T? = null
){
    class InternalServerError<T>() : ApiResult<T>()
    class Success<T>(code: String?, message: String? = null, body: T?) : ApiResult<T>(code,message, body)
    class Loading<T>(code: String? = null, message: String? = null, body: T? = null) : ApiResult<T>(code,message, body)
    class Error<T>( code: String?, message: String?, body: T?= null) : ApiResult<T>(code, message, body)
    class NoNetwork<T>() : ApiResult<T>()
}