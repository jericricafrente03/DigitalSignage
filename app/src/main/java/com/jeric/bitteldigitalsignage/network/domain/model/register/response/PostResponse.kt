package com.jeric.bitteldigitalsignage.network.domain.model.register.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("data")
    val postResponseData: PostResponseData,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: String,
    @SerializedName("room_number")
    val roomNumber: String
)