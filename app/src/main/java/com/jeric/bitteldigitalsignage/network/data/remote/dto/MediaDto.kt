package com.jeric.bitteldigitalsignage.network.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MediaDto(
    val id: Int,
    val orientation: String?,
    val name: String?,
    val description: String?,
    val zones: Int?,
    @SerializedName("type_id") val typeId: Int?,
    @SerializedName("preview_url") val previewUrl: String?,
    @SerializedName("preview_thumbnail_url") val previewThumbnailUrl: String?,
    @SerializedName("layout_id") val layoutId: Int?,
    @SerializedName("channel_id") val channelId: Int?,
    @SerializedName("deleted_at") val deletedAt: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
//    @SerializedName("tv_channel") val tvChannel: String?
)
