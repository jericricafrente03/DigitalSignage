package com.jeric.bitteldigitalsignage.network.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jeric.bitteldigitalsignage.network.domain.model.LayoutModel


class LayoutModelConverter {
    @TypeConverter
    fun fromLayoutModel(layoutModel: LayoutModel?): String? {
        if (layoutModel == null) {
            return null
        }
        return Gson().toJson(layoutModel)
    }

    @TypeConverter
    fun toLayoutModel(layoutModel: String?): LayoutModel? {
        if (layoutModel == null) {
            return null
        }
        val type = object : TypeToken<LayoutModel>() {}.type
        return Gson().fromJson(layoutModel, type)
    }

}
