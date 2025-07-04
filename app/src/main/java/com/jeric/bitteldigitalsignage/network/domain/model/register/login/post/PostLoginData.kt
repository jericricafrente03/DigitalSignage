package com.jeric.bitteldigitalsignage.network.domain.model.register.login.post

import com.google.gson.annotations.SerializedName

data class PostLoginData(
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String
)