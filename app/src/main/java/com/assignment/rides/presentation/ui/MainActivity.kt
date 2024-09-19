package com.assignment.rides.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.assignment.rides.R
import com.assignment.rides.data.model.VehicleModel
import com.assignment.rides.databinding.ActivityMainBinding
import com.assignment.rides.presentation.ui.fragments.VehicleDetailFragment
import com.assignment.rides.presentation.ui.fragments.VehicleListFragment
import com.assignment.rides.presentation.ui.inter.VehicleInterface
import com.assignment.rides.presentation.util.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), VehicleInterface {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(activityMainBinding.mainFrameLayout.id, VehicleListFragment(this@MainActivity))
                commit()
            }
        }

//        if (NetworkUtils.isNetworkAvailable(this))

    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }



    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun selectVehicleItem(vehicleModel: VehicleModel) {
        supportFragmentManager.beginTransaction().apply {
            replace(activityMainBinding.mainFrameLayout.id, VehicleDetailFragment(this@MainActivity,vehicleModel))
            addToBackStack(null)
            commit()
        }
    }

    override fun onFragmentBack() {
        onBackPressedDispatcher.onBackPressed()
    }

}