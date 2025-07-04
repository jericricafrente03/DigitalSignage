package com.jeric.bitteldigitalsignage.network.util

import okhttp3.internal.and
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object NetworkUtil {
    fun convertToHash(macAddress: String, appVersion: String = "5.0"): String {
        var sb: StringBuffer? = null
        try {
            val mac = macAddress+appVersion
            val md = MessageDigest.getInstance("MD5")
            md.update(mac.toByteArray())
            val byteData = md.digest()
            sb = StringBuffer()
            for (i in byteData.indices) {
                sb.append(
                    ((byteData[i] and 0xff) + 0x100)
                        .toString(16)
                        .substring(1)
                )
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return sb.toString()
    }
}