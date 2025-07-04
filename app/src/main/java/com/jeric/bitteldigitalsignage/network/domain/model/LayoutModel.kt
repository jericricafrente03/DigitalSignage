package com.jeric.bitteldigitalsignage.network.domain.model

import com.google.gson.annotations.SerializedName

data class LayoutModel(
    val id: Int,
    @SerializedName("stb_layout_id") val stbLayoutId: Int?,
    val name: String?,
    val zones: Int?,
    @SerializedName("preview_url") val previewUrl: String?,
    @SerializedName("preview_thumbnail_url") val previewThumbnailUrl: String?,
    @SerializedName("type_id") val typeId: Int?,
    @SerializedName("media_type_id") val mediaTypeId: Int?,
    @SerializedName("deleted_at") val deletedAt: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)