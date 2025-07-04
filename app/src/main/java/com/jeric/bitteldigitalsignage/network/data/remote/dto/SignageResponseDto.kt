package com.jeric.bitteldigitalsignage.network.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SignageResponseDto(
    @SerializedName("data") val data: SignageDataDto,
    @SerializedName("result") val result: String
)