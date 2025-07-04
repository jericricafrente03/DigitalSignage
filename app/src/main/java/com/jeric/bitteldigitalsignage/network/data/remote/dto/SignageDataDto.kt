package com.jeric.bitteldigitalsignage.network.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SignageDataDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("layout_id") val layoutId: Int,
    @SerializedName("img_uri") val imgUri: String,
    @SerializedName("img_thumbnail_uri") val imgThumbnailUri: String,
    @SerializedName("orientation") val orientation: String?,
    @SerializedName("deleted_at") val deletedAt: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("layout") val layout: LayoutDto,
    @SerializedName("zones") val zones: List<ZoneDto>
)