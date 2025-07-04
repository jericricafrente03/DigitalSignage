package com.jeric.bitteldigitalsignage.network.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ZoneMediaDto(
    @SerializedName("id") val id: Int,
    @SerializedName("media_id") val mediaId: Int,
    @SerializedName("zone_id") val zoneId: Int,
    @SerializedName("signage_id") val signageId: Int,
    @SerializedName("zone") val zone: Int,
    @SerializedName("time_start") val timeStart: String?,
    @SerializedName("time_end") val timeEnd: String?,
    @SerializedName("deleted_at") val deletedAt: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("orientation") val orientation: String?,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("type_id") val typeId: Int,
    @SerializedName("preview_url") val previewUrl: String?,
    @SerializedName("preview_thumbnail_url") val previewThumbnailUrl: String?,
    @SerializedName("layout_id") val layoutId: Int?,
    @SerializedName("channel_id") val channelId: Int?,
    @SerializedName("event_feeds") val eventFeeds: List<Any>,
    @SerializedName("tv_channel") val tvChannel: TvChannelDto?,
    @SerializedName("media_type_name") val mediaTypeName: String
)