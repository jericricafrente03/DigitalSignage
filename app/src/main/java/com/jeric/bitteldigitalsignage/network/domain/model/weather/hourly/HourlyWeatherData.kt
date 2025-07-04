package com.jeric.bitteldigitalsignage.network.domain.model.weather.hourly

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "hourly_weather")
data class HourlyWeatherData(
    val date: String,
    val description: String,
    val humidity: String,
    val icon: String,
    @PrimaryKey
    val id: Int,
    val temp: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
)