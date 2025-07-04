package com.jeric.bitteldigitalsignage.network.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneModel

class ListZoneConverter {
    @TypeConverter
    fun fromListZone(value: List<ZoneModel>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toListZone(value: String): List<ZoneModel> {
        val listType = object : TypeToken<List<ZoneModel>>() {}.type
        return Gson().fromJson(value, listType)
    }
}