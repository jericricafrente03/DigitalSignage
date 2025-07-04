package com.jeric.bitteldigitalsignage.network.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jeric.bitteldigitalsignage.network.data.local.converter.LayoutMediaModelConverter
import com.jeric.bitteldigitalsignage.network.data.local.converter.LayoutModelConverter
import com.jeric.bitteldigitalsignage.network.data.local.converter.ListEventFeedModelConverter
import com.jeric.bitteldigitalsignage.network.data.local.converter.ListZoneConverter
import com.jeric.bitteldigitalsignage.network.data.local.converter.ListZoneMediaModelConverter
import com.jeric.bitteldigitalsignage.network.data.local.dao.HomeDao
import com.jeric.bitteldigitalsignage.network.domain.model.MediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.SignageDataModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneModel

@Database(
    entities = [
        SignageDataModel::class,
        ZoneModel::class,
        ZoneMediaModel::class,
    ], version = 1 , exportSchema = false
)
@TypeConverters(
    LayoutModelConverter::class,
)
abstract class MeshDataBase : RoomDatabase() {
    abstract fun homeUiDao(): HomeDao
}