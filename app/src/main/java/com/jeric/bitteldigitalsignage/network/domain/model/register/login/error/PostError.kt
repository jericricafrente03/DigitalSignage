package com.jeric.bitteldigitalsignage.network.domain.model.register.login.error

import com.google.gson.annotations.SerializedName

data class PostError(
    @SerializedName("message")
    val message: String
)