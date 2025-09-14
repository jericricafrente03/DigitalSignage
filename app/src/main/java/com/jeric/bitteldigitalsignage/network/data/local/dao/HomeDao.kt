package com.jeric.bitteldigitalsignage.network.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jeric.bitteldigitalsignage.network.domain.model.EventFeedModel
import com.jeric.bitteldigitalsignage.network.domain.model.MediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.SignageDataModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneModel
import com.jeric.bitteldigitalsignage.network.domain.model.weather.daily.GetDailyData
import com.jeric.bitteldigitalsignage.network.domain.model.weather.hourly.HourlyWeatherData
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

    /*TodayWeather*/
    @Query("SELECT * FROM daily_weather")
    fun getDailyWeather(): List<GetDailyData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWeather(weather: List<GetDailyData>)

    @Query("DELETE FROM daily_weather")
    suspend fun deleteDailyWeather()

    /*TodayWeather*/
    @Query("SELECT * FROM hourly_weather")
    fun getHourlyWeather(): List<HourlyWeatherData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeather(weather: List<HourlyWeatherData>)

    @Query("DELETE FROM hourly_weather")
    suspend fun deleteHourlyWeather()


}