package com.jeric.bitteldigitalsignage.network.domain.model.register.post

import com.google.gson.annotations.SerializedName

data class PostRegistrationData(
    @SerializedName("api_key")
    val apiKey: String,
    @SerializedName("mac_address")
    val macAddress: String,
    @SerializedName("device_location")
    val deviceLocation: String,
    @SerializedName("version")
    val version: String = "5.0"
)