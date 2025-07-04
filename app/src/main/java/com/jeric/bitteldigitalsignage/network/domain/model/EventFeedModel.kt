package com.jeric.bitteldigitalsignage.network.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class EventFeedModel(
    @PrimaryKey
    val id: Int,
    @SerializedName("media_id") val mediaId: Int,
    val description: String?,
    val owner: String?,
    val location: String?,
    @SerializedName("img_uri") val imgUri: String?,
    @SerializedName("img_thumbnail_uri") val imgThumbnailUri: String?,
    @SerializedName("order_no") val orderNo: Int?,
    val start: String?,
    val end: String?,
    @SerializedName("deleted_at") val deletedAt: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)