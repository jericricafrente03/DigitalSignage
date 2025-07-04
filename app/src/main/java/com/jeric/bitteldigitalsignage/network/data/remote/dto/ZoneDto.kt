package com.jeric.bitteldigitalsignage.network.data.remote.dto

import com.google.gson.annotations.SerializedName


data class ZoneDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("layout_id") val layoutId: Int,
    @SerializedName("signage_id") val signageId: Int,
    @SerializedName("zone") val zone: Int,
    @SerializedName("start") val start: String?,
    @SerializedName("end") val end: String?,
    @SerializedName("sun") val sun: Int,
    @SerializedName("mon") val mon: Int,
    @SerializedName("tue") val tue: Int,
    @SerializedName("wed") val wed: Int,
    @SerializedName("thu") val thu: Int,
    @SerializedName("fri") val fri: Int,
    @SerializedName("sat") val sat: Int,
    @SerializedName("deleted_at") val deletedAt: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("zone_media") val zoneMedia: List<ZoneMediaDto>
)