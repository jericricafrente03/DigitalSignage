package com.jeric.bitteldigitalsignage.network.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "signageModel")
data class SignageDataModel(
    @PrimaryKey
    val id: Int,
    @SerializedName("img_thumbnail_uri")
    val imgThumbnailUri: String?,
    @SerializedName("img_uri")
    val imgUri: String?,
    @SerializedName("layout_id")
    val layoutId: Int,
    val name: String,
    val orientation: String,
    val layout: LayoutModel?,
)