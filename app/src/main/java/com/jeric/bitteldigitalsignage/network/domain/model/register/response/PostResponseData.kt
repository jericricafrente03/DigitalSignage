package com.jeric.bitteldigitalsignage.network.domain.model.register.response

import com.google.gson.annotations.SerializedName

data class PostResponseData(
    @SerializedName("action")
    val action: String,
    @SerializedName("class")
    val `class`: String,
    @SerializedName("device_id")
    val deviceId: Int,
    @SerializedName("room")
    val room: String,
    @SerializedName("username")
    val username: Any,
    @SerializedName("version")
    val version: String
)