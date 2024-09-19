package com.assignment.rides.data.model

import com.google.gson.annotations.SerializedName

data class VehicleModel(

	@field:SerializedName("drive_type")
	val driveType: String? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("car_options")
	val carOptions: List<String?>? = null,

	@field:SerializedName("make_and_model")
	val makeAndModel: String? = null,

	@field:SerializedName("car_type")
	val carType: String? = null,

	@field:SerializedName("doors")
	val doors: Int? = null,

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("specs")
	val specs: List<String?>? = null,

	@field:SerializedName("transmission")
	val transmission: String? = null,

	@field:SerializedName("license_plate")
	val licensePlate: String? = null,

	@field:SerializedName("kilometrage")
	val kilometrage: Int? = null,

	@field:SerializedName("vin")
	val vin: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("fuel_type")
	val fuelType: String? = null,

	@field:SerializedName("mileage")
	val mileage: Int? = null
)
