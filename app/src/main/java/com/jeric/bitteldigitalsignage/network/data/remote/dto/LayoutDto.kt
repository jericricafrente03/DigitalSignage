package com.jeric.bitteldigitalsignage.network.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LayoutDto(
    @SerializedName("id") val id: Int,
    @SerializedName("stb_layout_id") val stbLayoutId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("zones") val zones: Int,
    @SerializedName("preview_url") val previewUrl: String,
    @SerializedName("preview_thumbnail_url") val previewThumbnailUrl: String,
    @SerializedName("type_id") val typeId: Int,
    @SerializedName("media_type_id") val mediaTypeId: Int?,
    @SerializedName("deleted_at") val deletedAt: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)
