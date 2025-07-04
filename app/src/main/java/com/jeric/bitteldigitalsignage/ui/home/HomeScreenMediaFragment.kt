package com.jeric.bitteldigitalsignage.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.databinding.DigitalMediaLayoutBinding
import com.jeric.bitteldigitalsignage.datastore.model.STB
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.ui.components.LayoutManager
import com.jeric.bitteldigitalsignage.ui.components.MediaType
import com.jeric.bitteldigitalsignage.ui.components.startVLC
import com.jeric.bitteldigitalsignage.ui.weather.Extensions
import com.jeric.bitteldigitalsignage.ui.weather.WeatherAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.videolan.libvlc.util.VLCVideoLayout


@AndroidEntryPoint
class HomeScreenMediaFragment : Fragment() {
    private var _binding: DigitalMediaLayoutBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    var signageMediaData: ZoneMediaModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DigitalMediaLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (signageMediaData != null) {
                    display(signageMediaData!!)
                }
            }
        }
    }

    private suspend fun display(media: ZoneMediaModel) {
        coroutineScope {
            media.apply {
                when(typeId){
                    MediaType.IMAGE -> displayImage(media.previewUrl)
                    MediaType.VIDEO -> displayVLC(media.previewUrl)
                    MediaType.WEATHER -> displayWeather(media.layoutId, media)
                    MediaType.FEED -> {}
                    MediaType.WEATHER_FORECAST -> displayWeatherForecast(media.layoutId, media)
                    MediaType.TV -> displayVLC(media.tvChannel?.channelUri,true)
                    MediaType.SCROLLING -> displayScroll(media.description)
                    MediaType.TIME -> displayTime(media.layoutId, media)
                }
            }
        }
    }

    private suspend fun displayWeatherForecast(layoutId: Int?, media: ZoneMediaModel) {
//        inflateLayout(binding.weatherLayout, layoutId, MediaType.WEATHER, media)
//        homeViewModel.weatherUiState.collectLatest { weather ->
//            val weatherAdapter = WeatherAdapter(weather)
//            binding.rvStatistics.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
//            binding.rvStatistics.adapter = weatherAdapter
//        }
    }
    private suspend fun displayWeather(layoutId: Int?, media: ZoneMediaModel) {
        inflateLayout(binding.weatherLayout, layoutId, MediaType.WEATHER, media)
        homeViewModel.weatherUiStateToday.collectLatest { weather ->

            val ivIcon = view?.findViewById<ImageView>(R.id.iv_icon)
            val tvTemp = view?.findViewById<TextView>(R.id.tv_temp)
            val tvDesc = view?.findViewById<TextView>(R.id.tv_description)

            ivIcon?.setImageResource(Extensions.getDrawableResource(weather.icon))
            tvTemp?.text = context?.getString(R.string.weather_celcuis, weather.tempMin.toInt().toString())
            tvDesc?.text = weather.description
        }
    }

    private fun displayImage(imagePath: String?) {
        if (imagePath?.isNotEmpty() == true) {
            val imageUri = "${STB.HOST}:${STB.PORT}/" + imagePath
            Log.v("meme","imageUri -> $imageUri")
            binding.imageLayout.visibility = View.VISIBLE
            binding.ivDs.load(imageUri)
        }
    }
    private fun displayTime(layoutId: Int?, media: ZoneMediaModel) {
        inflateLayout(binding.timeLayout, layoutId, MediaType.TIME, media)
    }
    private fun displayScroll(desc: String?) {
        binding.scrollingLayout.visibility = View.VISIBLE
        binding.tvScroll.text = desc
    }

    private fun displayVLC(videoUri: String?, isLive: Boolean = false) {
        if (videoUri.isNullOrEmpty()) return

        binding.videoLayout.apply {
            visibility = View.VISIBLE
            val layout = requireActivity().layoutInflater.inflate(R.layout.digital_vlc_layout, this, false)
            addView(layout)
        }

        val rootView = view ?: return

        val videoLayout = rootView.findViewById<VLCVideoLayout>(R.id.vlc_layout)
        val progressFrame = rootView.findViewById<View>(R.id.progress_frame)

        val streamPath = if (isLive) {
            videoUri
        } else {
            "${STB.HOST}:${STB.PORT}/$videoUri"
        }

        startVLC(
            source = streamPath,
            layout = videoLayout,
            progressFrame = progressFrame,
            isLive = isLive,
            autoRestart = true
        ) { _, _ ->
            // Add any callback logic here if needed
        }
    }

    private fun inflateLayout(view: ViewGroup?, layoutId: Int?, type: Int?, media: ZoneMediaModel) {

        val dsLayoutManager = LayoutManager.getLayoutManager()
        view?.visibility = View.VISIBLE
        layoutId?.let {
            val layoutID = when (type) {
                MediaType.FEED -> dsLayoutManager.getFeedLayout(layoutId.toInt())
                MediaType.TIME -> dsLayoutManager.getTimeLayout(layoutId.toInt())
                MediaType.WEATHER -> dsLayoutManager.getWeatherLayout(layoutId.toInt())
                else -> 0
            }
            if (layoutID != 0) {
                val layout = requireActivity().layoutInflater.inflate(layoutID, view, false)
                layout?.let {
                    view?.addView(layout)
                }
                val name = view?.findViewById<TextView>(R.id.tv_title)
                media.let {
                    name?.text = media.name
                }
            }
        }

    }



}