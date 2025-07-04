package com.jeric.bitteldigitalsignage.network.domain.model.register.post

import com.google.gson.annotations.SerializedName

data class PostRegistration(
    @SerializedName("data")
    val postRegistrationData: PostRegistrationData
)