package com.jeric.bitteldigitalsignage.ui.main

import android.app.Application
import com.jeric.bitteldigitalsignage.http.HTTPConnection
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GoogleTVApp : Application(){
    override fun onCreate() {
        super.onCreate()
        HTTPConnection.startServer(5000)
    }
}