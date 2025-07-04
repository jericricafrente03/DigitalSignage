package com.jeric.bitteldigitalsignage.network.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jeric.bitteldigitalsignage.network.domain.model.MediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.SignageDataModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneModel
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
    @Query("SELECT * FROM signageModel")
    fun getAllSignage(): SignageDataModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSignage(signage: SignageDataModel)

    @Query("DELETE FROM signageModel")
    suspend fun deleteAllSignage()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZones(signage: List<ZoneModel>)

    @Query("SELECT * FROM zonemodel")
    fun getAllZones(): List<ZoneModel>

    @Query("DELETE FROM zonemodel")
    suspend fun deleteZones()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZonesMedia(signage: List<ZoneMediaModel>)

    @Query("SELECT * FROM zonemediamodel")
    fun getAllZonesMedia(): List<ZoneMediaModel>

    @Query("DELETE FROM zonemediamodel")
    suspend fun deleteZonesMedia()



}