package com.jeric.bitteldigitalsignage.network.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jeric.bitteldigitalsignage.network.data.local.converter.LayoutModelConverter
import com.jeric.bitteldigitalsignage.network.data.local.converter.TVChannelConverter
import com.jeric.bitteldigitalsignage.network.data.local.dao.HomeDao
import com.jeric.bitteldigitalsignage.network.domain.model.SignageDataModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneModel
import com.jeric.bitteldigitalsignage.network.domain.model.weather.daily.GetDailyData
import com.jeric.bitteldigitalsignage.network.domain.model.weather.hourly.HourlyWeatherData

@Database(
    entities = [
        SignageDataModel::class,
        ZoneModel::class,
        ZoneMediaModel::class,
        GetDailyData::class,
        HourlyWeatherData::class,
    ], version = 1 , exportSchema = false
)
@TypeConverters(
    LayoutModelConverter::class,
    TVChannelConverter::class
)
abstract class MeshDataBase : RoomDatabase() {
    abstract fun homeUiDao(): HomeDao
}