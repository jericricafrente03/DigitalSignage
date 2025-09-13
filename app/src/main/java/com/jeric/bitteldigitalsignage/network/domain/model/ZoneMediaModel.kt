package com.jeric.bitteldigitalsignage.network.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.jeric.bitteldigitalsignage.network.data.remote.dto.TvChannelDto


@Entity
data class ZoneMediaModel(
    @PrimaryKey
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
    @SerializedName("media_type_name") val mediaTypeName: String,
    @SerializedName("tv_channel") val tvChannel: TvChannelModel?,
//    @SerializedName("event_feeds") val eventFeedModel: List<EventFeedModel>?
)
