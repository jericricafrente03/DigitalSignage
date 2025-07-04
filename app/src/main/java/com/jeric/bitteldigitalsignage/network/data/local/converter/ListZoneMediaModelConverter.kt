package com.jeric.bitteldigitalsignage.network.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneModel

class ListZoneMediaModelConverter {
    @TypeConverter
    fun fromListZone(value: List<ZoneMediaModel>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toListZone(value: String): List<ZoneMediaModel> {
        val listType = object : TypeToken<List<ZoneMediaModel>>() {}.type
        return Gson().fromJson(value, listType)
    }
}