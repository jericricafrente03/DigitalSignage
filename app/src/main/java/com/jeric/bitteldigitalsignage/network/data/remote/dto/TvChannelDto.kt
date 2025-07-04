package com.jeric.bitteldigitalsignage.network.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TvChannelDto(
    @SerializedName("id") val id: Int,
    @SerializedName("channel") val channel: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("channel_uri") val channelUri: String,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("order_no") val orderNo: Int,
    @SerializedName("img_uri") val imgUri: String,
    @SerializedName("img_thumbnail_uri") val imgThumbnailUri: String,
    @SerializedName("is_enable") val isEnable: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("deleted_at") val deletedAt: String?
)