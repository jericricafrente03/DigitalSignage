package com.jeric.bitteldigitalsignage.network.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jeric.bitteldigitalsignage.network.domain.model.EventFeedModel

class ListEventFeedModelConverter {
    @TypeConverter
    fun fromEventFeedModel(value: List<EventFeedModel>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toEventFeedModel(value: String): List<EventFeedModel> {
        val listType = object : TypeToken<List<EventFeedModel>>() {}.type
        return Gson().fromJson(value, listType)
    }
}