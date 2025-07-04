package com.jeric.bitteldigitalsignage.network.domain.model.weather.hourly

data class GetHourlyWeather(
    val `data`: List<HourlyWeatherData>,
    val result: String
)