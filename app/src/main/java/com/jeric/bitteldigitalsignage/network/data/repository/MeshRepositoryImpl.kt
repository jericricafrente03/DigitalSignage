package com.jeric.bitteldigitalsignage.network.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.jeric.bitteldigitalsignage.datastore.DataStoreOperations
import com.jeric.bitteldigitalsignage.datastore.model.STB
import com.jeric.bitteldigitalsignage.network.data.local.MeshDataBase
import com.jeric.bitteldigitalsignage.network.data.mapper.toDomain
import com.jeric.bitteldigitalsignage.network.data.mapper.toMediaDomain
import com.jeric.bitteldigitalsignage.network.data.mapper.toZoneListDomain
import com.jeric.bitteldigitalsignage.network.data.mapper.toZoneMediaListDomain
import com.jeric.bitteldigitalsignage.network.data.mapper.toZoneModelDomain
import com.jeric.bitteldigitalsignage.network.data.remote.IptvListAPI
import com.jeric.bitteldigitalsignage.network.domain.model.MediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.SignageDataModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneModel
import com.jeric.bitteldigitalsignage.network.domain.model.register.login.post.PostLogin
import com.jeric.bitteldigitalsignage.network.domain.model.register.login.post.PostLoginData
import com.jeric.bitteldigitalsignage.network.domain.model.register.post.PostRegistration
import com.jeric.bitteldigitalsignage.network.domain.model.register.post.PostRegistrationData
import com.jeric.bitteldigitalsignage.network.domain.model.stb.StbRegistration
import com.jeric.bitteldigitalsignage.network.domain.model.time.GetTimeData
import com.jeric.bitteldigitalsignage.network.domain.model.weather.daily.GetDailyData
import com.jeric.bitteldigitalsignage.network.domain.model.weather.hourly.HourlyWeatherData
import com.jeric.bitteldigitalsignage.network.domain.repository.MeshRepository
import com.jeric.bitteldigitalsignage.network.util.DataState
import com.jeric.bitteldigitalsignage.network.util.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MeshRepositoryImpl @Inject constructor(
    private val api: IptvListAPI,
    private val dataStoreOperations: DataStoreOperations,
    private val meshDataBase: MeshDataBase,
) : MeshRepository {

    private val dataBase = meshDataBase.homeUiDao()


    override fun registerResult(stbRegistration: StbRegistration) = flow {
        emit(DataState.Loading())
        try {
            val response = api.registerResult(
                PostRegistration(
                    PostRegistrationData(
                        apiKey = STB.API_KEY,
                        macAddress = STB.MAC_ADDRESS,
                        deviceLocation = STB.ROOM,
                    )
                )
            )

            emit(
                if (response.isSuccessful) DataState.Success(response.body())
                else DataState.Error(response.errorBody()?.string() ?: "Error")
            )
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Network or unexpected error"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun registerLoginResult() {
        try {
            val response =
                api.registerLoginApi(PostLogin(PostLoginData(STB.MAC_ADDRESS, STB.API_KEY)))
            response.body()?.let {
                STB.API_TOKEN = it.token
                STB.TOKEN_EXPIRED_AT = it.tokenExpireAT
                dataStoreOperations.saveStbState(STB)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getTime(): DataState<GetTimeData> {
        return try {
            val response = api.getTime()
            DataState.Success(response.data)
        } catch (e: Exception) {
            DataState.Error(e.message ?: "Error")
        }
    }

    override fun getSignageDataModel() = flow {
        val response = api.getSignage()
        meshDataBase.withTransaction {
            dataBase.deleteAllSignage()
            response.data?.toDomain()?.let { dataBase.insertSignage(it) }
        }
        emit(dataBase.getAllSignage())
    }.flowOn(Dispatchers.IO)

    override fun getZoneModel() = flow {
        val response = api.getSignage()
        meshDataBase.withTransaction {
            dataBase.deleteZones()
            response.data?.zones?.toZoneListDomain()?.let { dataBase.insertZones(it) }
        }
        emit(dataBase.getAllZones())
    }.flowOn(Dispatchers.IO)

    override fun getDailyWeather() = flow {
//        val response = api.getWeatherDaily()
        meshDataBase.withTransaction {
            dataBase.deleteDailyWeather()
            dataBase.insertDailyWeather(getMockDailyWeatherList())
        }
        emit(dataBase.getDailyWeather())
    }.flowOn(Dispatchers.IO)

    override fun getHourlyWeather() = flow {
        val response = api.getWeatherHourly()
        meshDataBase.withTransaction {
            dataBase.deleteHourlyWeather()
            dataBase.insertHourlyWeather(response.data)
        }
        emit(dataBase.getHourlyWeather())
    }.flowOn(Dispatchers.IO)

    override fun getZoneMediaModel() = flow {
        val response = api.getSignage()
        meshDataBase.withTransaction {
            dataBase.deleteZonesMedia()
            response.data?.zones?.forEach {
                dataBase.insertZonesMedia(it.zoneMedia.toZoneMediaListDomain())
            }
        }
        emit(dataBase.getAllZonesMedia())
    }.flowOn(Dispatchers.IO)


    fun getMockDailyWeatherList(): List<GetDailyData> {
        return listOf(
            GetDailyData(
                id = 1,
                date = "2025-04-14 00:00:00",
                description = "Overcast",
                humidity = "87",
                icon = "3",
                windSpeed = 16.3,
                sunrise = "2025-04-14 05:43:00",
                sunset = "2025-04-14 18:10:00",
                tempDay = null,
                tempMin = 26.4,
                tempMax = 35.0,
                tempNight = null,
                tempEve = null,
                tempMorn = null,
                pressure = 1009,
                dewPoint = 25.2
            ),
            GetDailyData(
                id = 2,
                date = "2025-04-15 00:00:00",
                description = "Rain showers: Slight",
                humidity = "87",
                icon = "80",
                windSpeed = 13.8,
                sunrise = "2025-04-15 05:42:00",
                sunset = "2025-04-15 18:10:00",
                tempDay = null,
                tempMin = 26.7,
                tempMax = 35.1,
                tempNight = null,
                tempEve = null,
                tempMorn = null,
                pressure = 1009,
                dewPoint = 25.2
            ),
            GetDailyData(
                id = 3,
                date = "2025-04-16 00:00:00",
                description = "Partly cloudy",
                humidity = "87",
                icon = "2",
                windSpeed = 12.2,
                sunrise = "2025-04-16 05:42:00",
                sunset = "2025-04-16 18:10:00",
                tempDay = null,
                tempMin = 26.8,
                tempMax = 34.9,
                tempNight = null,
                tempEve = null,
                tempMorn = null,
                pressure = 1009,
                dewPoint = 25.2
            ),
            GetDailyData(
                id = 4,
                date = "2025-04-17 00:00:00",
                description = "Overcast",
                humidity = "87",
                icon = "3",
                windSpeed = 11.4,
                sunrise = "2025-04-17 05:41:00",
                sunset = "2025-04-17 18:10:00",
                tempDay = null,
                tempMin = 25.4,
                tempMax = 36.9,
                tempNight = null,
                tempEve = null,
                tempMorn = null,
                pressure = 1009,
                dewPoint = 25.2
            ),
            GetDailyData(
                id = 5,
                date = "2025-04-18 00:00:00",
                description = "Overcast",
                humidity = "87",
                icon = "3",
                windSpeed = 20.9,
                sunrise = "2025-04-18 05:41:00",
                sunset = "2025-04-18 18:10:00",
                tempDay = null,
                tempMin = 26.0,
                tempMax = 37.9,
                tempNight = null,
                tempEve = null,
                tempMorn = null,
                pressure = 1009,
                dewPoint = 25.2
            ),
            GetDailyData(
                id = 6,
                date = "2025-04-19 00:00:00",
                description = "Partly cloudy",
                humidity = "87",
                icon = "2",
                windSpeed = 16.2,
                sunrise = "2025-04-19 05:40:00",
                sunset = "2025-04-19 18:10:00",
                tempDay = null,
                tempMin = 27.1,
                tempMax = 37.7,
                tempNight = null,
                tempEve = null,
                tempMorn = null,
                pressure = 1009,
                dewPoint = 25.2
            ),
            GetDailyData(
                id = 7,
                date = "2025-04-20 00:00:00",
                description = "Partly cloudy",
                humidity = "87",
                icon = "2",
                windSpeed = 14.0,
                sunrise = "2025-04-20 05:39:00",
                sunset = "2025-04-20 18:10:00",
                tempDay = null,
                tempMin = 27.3,
                tempMax = 37.8,
                tempNight = null,
                tempEve = null,
                tempMorn = null,
                pressure = 1009,
                dewPoint = 25.2
            )
        )
    }
}
