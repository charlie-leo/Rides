package com.assignment.rides.presentation.ui.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.rides.data.model.VehicleModel
import com.assignment.rides.databinding.VehicleItemBinding
import com.assignment.rides.presentation.ui.inter.VehicleInterface

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */
class VehicleAdapter(
    private val vehicleInterface: VehicleInterface,
    private var vehicleList: List<VehicleModel>
) : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {


    private lateinit var vehicleItemBinding: VehicleItemBinding

    fun setVehicleList(vehicleList: List<VehicleModel>) {
//        val lastPosition = this.vehicleList.size
//        this.vehicleList.toMutableList().clear()
//        notifyItemRangeChanged(0, lastPosition)
//        this.vehicleList = vehicleList
//        notifyItemRangeInserted(0, this.vehicleList.size)

        val oldSize = this.vehicleList.size
        this.vehicleList = vehicleList
        if (oldSize == 0) {
            notifyItemRangeInserted(0, this.vehicleList.size)
        } else {
            notifyItemRangeChanged(0, oldSize.coerceAtLeast(this.vehicleList.size))
            if (this.vehicleList.size < oldSize) {
                notifyItemRangeRemoved(this.vehicleList.size, oldSize - this.vehicleList.size)
            }
        }
    }

    override fun onCreateViewHolder(container: ViewGroup, position: Int): VehicleViewHolder {
        vehicleItemBinding =
            VehicleItemBinding.inflate(LayoutInflater.from(container.context), container, false)
        return VehicleViewHolder(vehicleItemBinding)
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    override fun onBindViewHolder(viewHolder: VehicleViewHolder, position: Int) {

        val item = vehicleList[position]

        viewHolder.onBind(item)
        viewHolder.vehicleItemBinding.root.setOnClickListener {
            vehicleInterface.selectVehicleItem(item)
        }
    }

    class VehicleViewHolder(val vehicleItemBinding: VehicleItemBinding) :
        RecyclerView.ViewHolder(vehicleItemBinding.root) {

        fun onBind(item: VehicleModel) {
            val vinText = "vin : ${item.vin}"
            vehicleItemBinding.vin.text = vinText
            val makeModelText = "Make & Model : ${item.makeAndModel}"
            vehicleItemBinding.makeModel.text = makeModelText
        }
    }
}