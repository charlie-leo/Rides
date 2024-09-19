package com.assignment.rides.presentation.ui.viewmodel

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.rides.data.model.VehicleModel
import com.assignment.rides.domain.usecase.FetchVehicleUseCase
import com.assignment.rides.presentation.util.ApiResult
import com.assignment.rides.presentation.util.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */


@HiltViewModel
class VehicleListViewModel @Inject constructor(
    private val fetchVehicleUseCase: FetchVehicleUseCase
) : ViewModel() {


    private val TAG = "VehicleListViewModel"
    private var jobs = Job().job

    private val _vehicleListFlow = MutableStateFlow(listOf<VehicleModel>())
    val vehicleListFlow: StateFlow<List<VehicleModel>> = _vehicleListFlow

    private val _loaderFlow = MutableStateFlow(false)
    val loaderFlow: StateFlow<Boolean> = _loaderFlow

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: SharedFlow<String> = _errorFlow

    init {

    }


    fun fetchVehicleList(count: String) {

        jobs = viewModelScope.launch {
            if (count.isDigitsOnly()) {
                try {
                    _loaderFlow.value = true
                    fetchVehicleUseCase.execute(count.toInt()) { result ->
                        _loaderFlow.value = false
                        when (result) {
                            is ApiResult.Success -> {
                                val vehList = result.body ?: listOf()
                                _vehicleListFlow.value = vehList.sortedBy { it.vin }
                            }

                            is ApiResult.Error -> emitError(result.message ?: "Bad Request")
                            is ApiResult.InternalServerError -> emitError(
                                result.message ?: "Something went wrong, Try later."
                            )

                            is ApiResult.Loading -> {}
                            is ApiResult.NoNetwork -> emitError(
                                result.message ?: "Something went wrong, Try later."
                            )
                        }
                    }
                } catch (exception: Exception) {
                    _loaderFlow.value = false
                    emitError("Something went wrong, Try later.")
                    Log.e(TAG, "fetchVehicleList: ${exception.message}", exception)
                }
            }
        }
    }

    private fun emitError(error: String) {
        viewModelScope.launch {
            _errorFlow.emit(error)
        }
    }


    override fun onCleared() {
        super.onCleared()
        jobs.cancel()
    }

}