package com.assignment.rides.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.assignment.rides.databinding.CarbonEmissionSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */
class CarbonEmissionSheetFragment(private val kilometerAge: Int?) : BottomSheetDialogFragment() {


    private lateinit var carbonEmissionSheetBinding: CarbonEmissionSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        carbonEmissionSheetBinding = CarbonEmissionSheetBinding.inflate(inflater)
        return carbonEmissionSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kilometerAge?.let {
            val title = "Carbon Estimated for ${kilometerAge} Km."
            carbonEmissionSheetBinding.carbonSheetTitle.text = title

            val text = "Estimated Carbon Emission: ${calculateEmissions(kilometerAge = it).toString()} units"
            carbonEmissionSheetBinding.carbonEstimation.text = text
        }

        carbonEmissionSheetBinding.closeSheet.setOnClickListener {
            dismiss()
        }
    }

    fun calculateEmissions(kilometerAge: Int): Double {
        return if (kilometerAge <= 5000) {
            kilometerAge * 1.0
        } else {
            (5000 * 1.0) + ((kilometerAge - 5000) * 1.5)
        }
    }

}