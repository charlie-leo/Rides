package com.assignment.rides.presentation.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.rides.data.model.VehicleModel
import com.assignment.rides.databinding.FragmentVehicleListBinding
import com.assignment.rides.presentation.ui.adaptors.VehicleAdapter
import com.assignment.rides.presentation.ui.inter.VehicleInterface
import com.assignment.rides.presentation.ui.viewmodel.VehicleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */

@AndroidEntryPoint
class VehicleListFragment(private val vehicleInterface: VehicleInterface) : Fragment() {

    private val TAG = "VehicleListFragment"

    private lateinit var fragmentVehicleListBinding: FragmentVehicleListBinding
    private lateinit var vehicleListViewModel: VehicleListViewModel

    private var jobs = Job().job

    private var vehicleAdapter: VehicleAdapter? = null
    private var count : String = ""
    private var vehicleList : List<VehicleModel> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentVehicleListBinding = FragmentVehicleListBinding.inflate(inflater, container, false)

        vehicleListViewModel = ViewModelProvider(this)[VehicleListViewModel::class.java]
        return fragmentVehicleListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentVehicleListBinding.fetchBtn.setOnClickListener {
            fragmentVehicleListBinding.countError.text = " "
            val tempCount = fragmentVehicleListBinding.vehicleCount.text.toString()
            count = if (vehicleListViewModel.validateCount(tempCount).first) tempCount else count

            vehicleListViewModel.fetchVehicleList(tempCount)

            fragmentVehicleListBinding.vehicleCount.setText("")
            fragmentVehicleListBinding.vehicleCount.clearFocus()
        }

        fragmentVehicleListBinding.vehicleSwipeRefresh.setOnRefreshListener {
            vehicleListViewModel.fetchVehicleList(count)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vehicleListViewModel.vehicleListFlow.collectLatest { list ->
                    if (vehicleAdapter == null) {
                        vehicleAdapter = VehicleAdapter(vehicleInterface, list)

                        fragmentVehicleListBinding.vehicleListRecyclerView.layoutManager =
                            LinearLayoutManager(view.context)
                        fragmentVehicleListBinding.vehicleListRecyclerView.adapter = vehicleAdapter
                    } else {
                        vehicleAdapter?.setVehicleList(list)
                        if (fragmentVehicleListBinding.vehicleListRecyclerView.adapter == null){
                            fragmentVehicleListBinding.vehicleListRecyclerView.layoutManager =
                                LinearLayoutManager(view.context)
                            fragmentVehicleListBinding.vehicleListRecyclerView.adapter = vehicleAdapter
                        }
                    }
                    fragmentVehicleListBinding.vehicleSwipeRefresh.isRefreshing = false
                    val resCount = if (!TextUtils.isEmpty(count)) "Result Count `$count`" else ""
                    fragmentVehicleListBinding.searchCount.text = resCount
                }
            }
        }

        lifecycleScope.launch {
            vehicleListViewModel.errorFlow.collect { error ->
                launch(Dispatchers.Main) {
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
                fragmentVehicleListBinding.vehicleSwipeRefresh.isRefreshing = false
            }
        }
        lifecycleScope.launchWhenStarted {
            vehicleListViewModel.loaderFlow.collect { isLoading ->
                launch(Dispatchers.Main) {
                    fragmentVehicleListBinding.loader.visibility =
                        if (isLoading) View.VISIBLE else View.GONE
                }

            }
        }

        lifecycleScope.launch {
            vehicleListViewModel.countErrorFlow.collect { error ->
                launch(Dispatchers.Main) {
                    fragmentVehicleListBinding.countError.text = error
                }
                fragmentVehicleListBinding.vehicleSwipeRefresh.isRefreshing = false
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }



    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ")
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }
}