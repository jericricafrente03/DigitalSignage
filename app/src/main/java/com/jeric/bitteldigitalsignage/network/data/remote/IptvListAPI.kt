package com.jeric.bitteldigitalsignage.network.data.remote

import com.jeric.bitteldigitalsignage.datastore.model.STB
import com.jeric.bitteldigitalsignage.network.data.remote.dto.SignageResponseDto
import com.jeric.bitteldigitalsignage.network.domain.model.register.login.post.PostLogin
import com.jeric.bitteldigitalsignage.network.domain.model.register.login.response.ResponseLogin
import com.jeric.bitteldigitalsignage.network.domain.model.register.post.PostRegistration
import com.jeric.bitteldigitalsignage.network.domain.model.register.response.PostResponse
import com.jeric.bitteldigitalsignage.network.domain.model.time.GetTime
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IptvListAPI {

    @POST("index.php/api/stb_register")
    suspend fun registerResult(
        @Body requestLicense: PostRegistration
    ): Response<PostResponse>

    @POST("index.php/api/login")
    suspend fun registerLoginApi(
        @Body login: PostLogin
    ): Response<ResponseLogin>


    @GET("index.php/api/get_signage")
    suspend fun getSignage(
        @Query("mac_address") macAddress: String = STB.MAC_ADDRESS
    ): SignageResponseDto


    @GET("index.php/api/stb_time")
    suspend fun getTime(
        @Query("room") room: String = STB.ROOM
    ): GetTime

}