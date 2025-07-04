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
            dataBase.insertSignage(response.data.toDomain())
        }
        emit(dataBase.getAllSignage())
    }.flowOn(Dispatchers.IO)

    override fun getZoneModel() = flow {
        val response = api.getSignage()
        meshDataBase.withTransaction {
            dataBase.deleteZones()
            dataBase.insertZones(response.data.zones.toZoneListDomain())
        }
        emit(dataBase.getAllZones())
    }.flowOn(Dispatchers.IO)

    override fun getZoneMediaModel() = flow {
        val response = api.getSignage()
        meshDataBase.withTransaction {
            dataBase.deleteZonesMedia()
            response.data.zones.forEach {
                dataBase.insertZonesMedia(it.zoneMedia.toZoneMediaListDomain())
            }
        }
        emit(dataBase.getAllZonesMedia())
    }.flowOn(Dispatchers.IO)



}
