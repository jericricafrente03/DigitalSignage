package com.jeric.bitteldigitalsignage.network.data.mapper

import com.jeric.bitteldigitalsignage.network.data.remote.dto.LayoutDto
import com.jeric.bitteldigitalsignage.network.data.remote.dto.MediaDto
import com.jeric.bitteldigitalsignage.network.data.remote.dto.SignageDataDto
import com.jeric.bitteldigitalsignage.network.data.remote.dto.ZoneDto
import com.jeric.bitteldigitalsignage.network.data.remote.dto.ZoneMediaDto
import com.jeric.bitteldigitalsignage.network.domain.model.GetSignageModel
import com.jeric.bitteldigitalsignage.network.domain.model.LayoutModel
import com.jeric.bitteldigitalsignage.network.domain.model.MediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.SignageDataModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneModel

fun SignageDataDto.toDomain(): SignageDataModel {
    return SignageDataModel(
        id = this.id,
        imgThumbnailUri = this.imgThumbnailUri,
        imgUri = this.imgUri,
        layoutId = this.layoutId,
        name = this.name,
        orientation = this.orientation ?: "Landscape",
        layout = this.layout.toLayoutDomain()
    )
}

fun LayoutDto.toLayoutDomain(): LayoutModel {
    return LayoutModel(
        id, stbLayoutId, name, zones, previewUrl, previewThumbnailUrl, typeId, mediaTypeId, deletedAt, createdAt, updatedAt
    )
}

fun ZoneMediaDto.toZoneModelDomain(): ZoneMediaModel {
    return ZoneMediaModel(
        id, mediaId, zoneId, signageId, zone, timeStart, timeEnd, deletedAt, createdAt, updatedAt, orientation, name, description, typeId, previewUrl, previewThumbnailUrl, layoutId, channelId, mediaTypeName
    )
}

fun ZoneDto.toZoneDomain(): ZoneModel {
    return ZoneModel(
        id, name, layoutId, signageId, zone, start, end, sun, mon, tue, wed, thu, fri, sat, deletedAt, createdAt, updatedAt
    )
}

fun MediaDto.toMediaDomain(): MediaModel {
    return MediaModel(
        id,orientation,name,description,zones,typeId,previewUrl,previewThumbnailUrl,layoutId,channelId,deletedAt,createdAt,updatedAt, tvChannel = ""
    )
}

fun List<ZoneDto>.toZoneListDomain(): List<ZoneModel>{
    return this.map { it.toZoneDomain() }
}

fun List<ZoneMediaDto>.toZoneMediaListDomain(): List<ZoneMediaModel>{
    return this.map { it.toZoneModelDomain() }
}



