package com.jeric.bitteldigitalsignage.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.http.HTTPConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}