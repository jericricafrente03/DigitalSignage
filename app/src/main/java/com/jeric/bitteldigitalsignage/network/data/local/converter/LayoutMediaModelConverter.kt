package com.jeric.bitteldigitalsignage.network.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jeric.bitteldigitalsignage.network.domain.model.MediaModel


class LayoutMediaModelConverter {
    @TypeConverter
    fun fromLayoutModel(layoutModel: MediaModel?): String? {
        if (layoutModel == null) {
            return null
        }
        return Gson().toJson(layoutModel)
    }

    @TypeConverter
    fun toLayoutModel(layoutModel: String?): MediaModel? {
        if (layoutModel == null) {
            return null
        }
        val type = object : TypeToken<MediaModel>() {}.type
        return Gson().fromJson(layoutModel, type)
    }

}
