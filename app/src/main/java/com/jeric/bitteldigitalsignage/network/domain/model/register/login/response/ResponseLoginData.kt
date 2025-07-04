package com.jeric.bitteldigitalsignage.network.domain.model.register.login.response

import com.google.gson.annotations.SerializedName

data class ResponseLoginData(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any,
    @SerializedName("firstname")
    val firstname: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: Any,
    @SerializedName("initials_random_color")
    val initialsRandomColor: Any,
    @SerializedName("is_stb")
    val isStb: Int,
    @SerializedName("lastname")
    val lastname: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("role_id")
    val roleId: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)