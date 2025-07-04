package com.jeric.bitteldigitalsignage.network.domain.manager

import com.jeric.bitteldigitalsignage.datastore.DataStoreOperations
import com.jeric.bitteldigitalsignage.datastore.model.STB
import kotlinx.coroutines.Dispatchers
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BaseUrlInterceptor @Inject constructor(
    pref: DataStoreOperations
) : Interceptor {

    companion object {
        var newBaseUrl = STB.HOST + ":" + STB.PORT + "/"
        fun setBaseUrl(baseUrl: String) {
            newBaseUrl = baseUrl
        }
    }

    init {
        pref.readStbFlow(Dispatchers.IO) {
            newBaseUrl = it.HOST + ":" + it.PORT + "/"
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val parsedUrl: HttpUrl = newBaseUrl.toHttpUrlOrNull() ?: return chain.proceed(originalRequest)

        val newUrl = originalRequest.url.newBuilder()
            .scheme(parsedUrl.scheme)
            .host(parsedUrl.host)
            .port(parsedUrl.port)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .addHeader("Authorization", "Bearer ${STB.API_TOKEN}")
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(newRequest)
    }
}
