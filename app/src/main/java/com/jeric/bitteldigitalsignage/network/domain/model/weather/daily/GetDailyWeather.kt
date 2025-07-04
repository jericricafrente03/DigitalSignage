package com.jeric.bitteldigitalsignage.network.domain.model.weather.daily

data class GetDailyWeather(
    val `data`: List<GetDailyData>,
    val result: String
)