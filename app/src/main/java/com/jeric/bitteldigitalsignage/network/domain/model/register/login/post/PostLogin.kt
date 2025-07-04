package com.jeric.bitteldigitalsignage.network.domain.model.register.login.post

import com.google.gson.annotations.SerializedName

data class PostLogin(
    @SerializedName("data")
    val postLoginData: PostLoginData
)