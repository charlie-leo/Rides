package com.assignment.rides.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.assignment.rides.data.model.VehicleModel
import com.assignment.rides.databinding.FragmentVehicleDetailsBinding
import com.assignment.rides.presentation.ui.inter.VehicleInterface
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */

@AndroidEntryPoint
class VehicleDetailFragment(private val vehicleInterface: VehicleInterface, private val vehicleModel: VehicleModel?) : Fragment() {


    private lateinit var fragmentVehicleDetailsBinding: FragmentVehicleDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentVehicleDetailsBinding = FragmentVehicleDetailsBinding.inflate(inflater,container,false)
        return fragmentVehicleDetailsBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vehicleModel?.let {
            fragmentVehicleDetailsBinding.vinText.text = it.vin
            fragmentVehicleDetailsBinding.mmText.text = it.makeAndModel
            fragmentVehicleDetailsBinding.colorText.text = it.color
            fragmentVehicleDetailsBinding.carTypeText.text = it.carType
        }

        fragmentVehicleDetailsBinding.backIcon.setOnClickListener {
            vehicleInterface.onFragmentBack()
        }

        fragmentVehicleDetailsBinding.calculateEmissionBtn.setOnClickListener {
            val carbonEmissionSheetFragment = CarbonEmissionSheetFragment(vehicleModel?.kilometrage)
            carbonEmissionSheetFragment.show(childFragmentManager,carbonEmissionSheetFragment.tag)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}