package com.jeric.bitteldigitalsignage.network.domain.model.register.login.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("data")
    val responseLoginData: ResponseLoginData,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("token_expires_at")
    val tokenExpireAT: String
)