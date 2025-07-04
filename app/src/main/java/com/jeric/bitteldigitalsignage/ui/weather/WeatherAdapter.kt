package com.jeric.bitteldigitalsignage.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.databinding.FragmentWeatherDailyContentLayoutBinding
import com.jeric.bitteldigitalsignage.network.domain.model.weather.daily.GetDailyData
import com.jeric.bitteldigitalsignage.network.domain.model.weather.hourly.HourlyWeatherData
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherAdapter(
    private val weatherList: List<GetDailyData>
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: FragmentWeatherDailyContentLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetDailyData) {
            binding.apply {
                ivWeather.load(Extensions.getDrawableResource(item.icon))
                tvDailyCelcius.text = root.context.getString(R.string.weather_celcuis, item.tempMin.toInt().toString())
                tvDailyDescription.text = Extensions.capitalizeWord(item.description)
            }
        }
    }

    private fun String.parseDateToDay(): String {
        val dateFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(this)
        return date?.let { dateFormatter.format(it) }.orEmpty()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentWeatherDailyContentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = weatherList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return minOf(weatherList.size, MAX_ITEMS)
    }

    companion object {
        private const val MAX_ITEMS = 4
    }
}