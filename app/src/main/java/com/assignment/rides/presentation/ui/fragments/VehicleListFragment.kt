package com.assignment.rides.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.rides.databinding.FragmentVehicleListBinding
import com.assignment.rides.presentation.ui.adaptors.VehicleAdapter
import com.assignment.rides.presentation.ui.inter.VehicleInterface
import com.assignment.rides.presentation.ui.viewmodel.VehicleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */

@AndroidEntryPoint
class VehicleListFragment(private val vehicleInterface: VehicleInterface) : Fragment() {


    private lateinit var fragmentVehicleListBinding: FragmentVehicleListBinding
    private lateinit var vehicleListViewModel : VehicleListViewModel

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
            val count = fragmentVehicleListBinding.vehicleCount.text.toString()
            vehicleListViewModel.fetchVehicleList(count)
            fragmentVehicleListBinding.vehicleCount.setText("")
            fragmentVehicleListBinding.vehicleCount.clearFocus()
        }


        lifecycleScope.launch {
            vehicleListViewModel.vehicleListFlow.collectLatest { list ->
                val vehicleAdapter = VehicleAdapter(vehicleInterface, list)
                fragmentVehicleListBinding.vehicleListRecyclerView.layoutManager =
                    LinearLayoutManager(view.context)
                fragmentVehicleListBinding.vehicleListRecyclerView.adapter = vehicleAdapter

            }
        }
        lifecycleScope.launch {
            vehicleListViewModel.errorFlow.collect { error ->
                launch(Dispatchers.Main) {
                    Toast.makeText(view.context, error,Toast.LENGTH_SHORT).show()
                }

            }
        }
        lifecycleScope.launch {
            vehicleListViewModel.loaderFlow.collect { isLoading ->
                launch(Dispatchers.Main) {
                    fragmentVehicleListBinding.loader.visibility = if(isLoading) View.VISIBLE else View.GONE
                }

            }
        }

    }



    override fun onDestroy() {
        super.onDestroy()
    }

}