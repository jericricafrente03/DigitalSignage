package com.jeric.bitteldigitalsignage.network.domain.model.weather.daily

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "daily_weather")
data class GetDailyData(
    val date: String,
    val description: String,
    @SerializedName("dew_point")
    val dewPoint: Double,
    val humidity: String,
    val icon: String,
    @PrimaryKey
    val id: Int,
    val pressure: Int,
    val sunrise: String,
    val sunset: String,
    @SerializedName("temp_day")
    val tempDay: String?,
    @SerializedName("temp_eve")
    val tempEve: String?,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_morn")
    val tempMorn: String?,
    @SerializedName("temp_night")
    val tempNight: String?,
    @SerializedName("wind_speed")
    val windSpeed: Double
)