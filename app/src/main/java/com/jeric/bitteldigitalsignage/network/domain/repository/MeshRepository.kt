package com.jeric.bitteldigitalsignage.network.domain.repository

import com.jeric.bitteldigitalsignage.network.domain.model.GetSignageModel
import com.jeric.bitteldigitalsignage.network.domain.model.MediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.SignageDataModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneModel
import com.jeric.bitteldigitalsignage.network.domain.model.register.response.PostResponse
import com.jeric.bitteldigitalsignage.network.domain.model.stb.StbRegistration
import com.jeric.bitteldigitalsignage.network.domain.model.time.GetTimeData
import com.jeric.bitteldigitalsignage.network.domain.model.weather.daily.GetDailyData
import com.jeric.bitteldigitalsignage.network.domain.model.weather.hourly.HourlyWeatherData
import com.jeric.bitteldigitalsignage.network.util.DataState
import kotlinx.coroutines.flow.Flow

interface MeshRepository {
    fun registerResult(stbRegistration: StbRegistration): Flow<DataState<PostResponse>>
    suspend fun registerLoginResult()

    suspend fun getTime(): DataState<GetTimeData>
//    suspend fun getSignageResult()

    /*Data*/
    fun getSignageDataModel(): Flow<SignageDataModel>
    fun getZoneMediaModel(): Flow<List<ZoneMediaModel>>
    fun getZoneModel(): Flow<List<ZoneModel>>

    fun getDailyWeather(): Flow<List<GetDailyData>>
    fun getHourlyWeather(): Flow<List<HourlyWeatherData>>
}