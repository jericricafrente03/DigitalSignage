package com.jeric.bitteldigitalsignage.network.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jeric.bitteldigitalsignage.network.domain.model.LayoutModel
import com.jeric.bitteldigitalsignage.network.domain.model.TvChannelModel


class TVChannelConverter {
    @TypeConverter
    fun fromLayoutModel(layoutModel: TvChannelModel?): String? {
        if (layoutModel == null) {
            return null
        }
        return Gson().toJson(layoutModel)
    }

    @TypeConverter
    fun toLayoutModel(layoutModel: String?): TvChannelModel? {
        if (layoutModel == null) {
            return null
        }
        val type = object : TypeToken<TvChannelModel>() {}.type
        return Gson().fromJson(layoutModel, type)
    }

}
