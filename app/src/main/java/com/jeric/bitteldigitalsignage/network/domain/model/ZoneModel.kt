package com.jeric.bitteldigitalsignage.network.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "zoneModel")
data class ZoneModel(
    @PrimaryKey
    val id: Int,
    val name: String?,
    @SerializedName("layout_id") val layoutId: Int,
    @SerializedName("signage_id") val signageId: Int,
    val zone: Int,
    val start: String?,
    val end: String?,
    val sun: Int,
    val mon: Int,
    val tue: Int,
    val wed: Int,
    val thu: Int,
    val fri: Int,
    val sat: Int,
    @SerializedName("deleted_at") val deletedAt: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
)