package com.jeric.bitteldigitalsignage.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.databinding.DigitalMediaLayoutBinding
import com.jeric.bitteldigitalsignage.datastore.model.STB
import com.jeric.bitteldigitalsignage.network.domain.model.EventFeedModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.ui.components.LayoutManager
import com.jeric.bitteldigitalsignage.ui.components.MediaType
import com.jeric.bitteldigitalsignage.ui.components.startVLC
import com.jeric.bitteldigitalsignage.ui.controller.FeedAdapter
import com.jeric.bitteldigitalsignage.ui.controller.FeedAdapterTwice
import com.jeric.bitteldigitalsignage.ui.runninglayout.Layout21
import com.jeric.bitteldigitalsignage.ui.runninglayout.Layout24
import com.jeric.bitteldigitalsignage.ui.runninglayout.Layout42
import com.jeric.bitteldigitalsignage.ui.runninglayout.Layout57
import com.jeric.bitteldigitalsignage.ui.runninglayout.Layout58
import com.jeric.bitteldigitalsignage.ui.runninglayout.Layout59
import com.jeric.bitteldigitalsignage.ui.weather.Extensions
import com.jeric.bitteldigitalsignage.ui.weather.WeatherAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.videolan.libvlc.util.VLCVideoLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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
                    MediaType.FEED -> displayFeeds(media)
                    MediaType.TV -> displayVLC(media.tvChannel?.channelUri,true)
                    MediaType.SCROLLING -> displayScroll(media.description)
                    MediaType.TIME -> displayTime(media.layoutId, media)
                }
            }
        }
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
        ) { _, _ -> }
    }

    suspend fun displayFeeds(media: ZoneMediaModel) {
        homeViewModel.getEventFeedModel()
        val layout57 = view?.findViewById<Layout57>(R.id.feed_scroll)
        val layout58 = view?.findViewById<Layout58>(R.id.feed_scroll58)
        val layout59 = view?.findViewById<Layout59>(R.id.feed_scroll59)
        val layout21 = view?.findViewById<Layout21>(R.id.feed_scroll21)
        val layout24 = view?.findViewById<Layout24>(R.id.feed_scroll24)
        val layout42 = view?.findViewById<Layout42>(R.id.feed_scroll42)

        homeViewModel.eventFeed.collectLatest { feeds ->
            if (feeds.isNotEmpty()) {
                var feeds = feeds
                feeds = feeds.filter { feed ->
                    feed.mediaId == media.mediaId
                }
                Log.v("meme","eventFeed -> $feeds")
                when (media.layoutId.toString()) {
                    "47" -> {
                        layout57?.visibility = View.VISIBLE
                        layout57?.setData(feeds)
                    }

                    "48" -> {
                        layout58?.visibility = View.VISIBLE
                        layout58?.layout58(feeds, media.name)
                    }

                    "49" -> {
                        layout59?.visibility = View.VISIBLE
                        layout59?.layout59(feeds, media.name)
                    }

                    "44" -> {
                        layout42?.visibility = View.VISIBLE
                        layout42?.layout42(feeds, media.name)
                    }

                    "38" -> {
                        layout21?.visibility = View.VISIBLE
                        layout21?.layout21(feeds, media.name)
                        getListFeed(feeds)
                    }

                    "41" -> {
                        layout24?.visibility = View.VISIBLE
                        layout24?.setData(feeds, media.name)
                    }

                    "39" -> {
                        feeds = feeds.filter { feed ->
                            val format = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault())
                            val end = format.parse(feed.end)
                            Date().before(end)
                        }
                        inflateLayout(binding.feedLayout, media.layoutId, MediaType.FEED, media)
                        val typeface = ResourcesCompat.getFont(requireContext(), R.font.itc)
                        val typeface2 = ResourcesCompat.getFont(requireContext(), R.font.itclight)
                        val clock1 = view?.findViewById<TextView>(R.id.clock1)
                        val clock2 = view?.findViewById<TextView>(R.id.clock2)
                        val clock3 = view?.findViewById<TextView>(R.id.clock3)
                        clock1?.typeface = typeface
                        clock2?.typeface = typeface
                        clock3?.typeface = typeface2
                        val rvFeed = view?.findViewById<RecyclerView>(R.id.rv_feed)
                        val rvFeed2 = view?.findViewById<RecyclerView>(R.id.rv_feed2)
                        val feedAdapter = FeedAdapter(feeds, media.layoutId?.toInt() ?: 0)
                        val feedAdapters = FeedAdapterTwice(feeds, media.layoutId?.toInt() ?: 0)
                        rvFeed?.layoutManager = LinearLayoutManager(requireContext())
                        rvFeed2?.layoutManager = LinearLayoutManager(requireContext())
                        rvFeed?.adapter = feedAdapter
                        rvFeed2?.adapter = feedAdapters
                        feedAdapter.submitList(feeds.sortedWith(FeedTimeComparator()))
                        feedAdapters.submitList(feeds.sortedWith(FeedTimeComparator()))
                        getListFeedOrder(feeds.sortedWith(Reorder()))
                    }

                    else -> {
                        feeds = feeds.filter { feed ->
                            val format = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault())
                            val end = format.parse(feed.end)
                            Date().before(end)
                        }
                        inflateLayout(binding.feedLayout, media.layoutId, MediaType.FEED, media)
                        val typeface = ResourcesCompat.getFont(requireContext(), R.font.itc)
                        val typeface2 = ResourcesCompat.getFont(requireContext(), R.font.itclight)
                        val clock1 = view?.findViewById<TextView>(R.id.clock1)
                        val clock2 = view?.findViewById<TextView>(R.id.clock2)
                        val clock3 = view?.findViewById<TextView>(R.id.clock3)
                        clock1?.typeface = typeface
                        clock2?.typeface = typeface
                        clock3?.typeface = typeface2
                        val rvFeed = view?.findViewById<RecyclerView>(R.id.rv_feed)
                        val feedAdapter = FeedAdapter(feeds, media.layoutId ?: 0)
                        rvFeed?.layoutManager = LinearLayoutManager(requireContext())
                        rvFeed?.adapter = feedAdapter
                        feedAdapter.submitList(feeds.sortedWith(FeedTimeComparator()))
                        getListFeedOrder(feeds.sortedWith(Reorder()))
                    }
                }
            }


        }



    }

    private fun inflateLayout(view: ViewGroup?, layoutId: Int?, type: Int?, media: ZoneMediaModel) {

        val dsLayoutManager = LayoutManager.getLayoutManager()
        view?.visibility = View.VISIBLE
        layoutId?.let {
            val layoutID = when (type) {
                MediaType.FEED -> dsLayoutManager.getFeedLayout(layoutId)
                MediaType.TIME -> dsLayoutManager.getTimeLayout(layoutId)
                MediaType.WEATHER -> dsLayoutManager.getWeatherLayout(layoutId)
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

    private suspend fun getListFeed(data: List<EventFeedModel>) {
        var timeDelay: Long
        val format = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault())
        var getActiveFeeds = data
        getActiveFeeds = getActiveFeeds.filter {
            val end = format.parse(it.end)
            Date().before(end)
        }
        while (true){
            getActiveFeeds.forEach {
                val scheduleEndDate = format.parse(it.end)
                timeDelay = scheduleEndDate!!.time - Date().time
                delay(timeDelay)
//                Commands.reCreate.emit(true)
            }
            delay(5000)
        }
    }

    private suspend fun getListFeedOrder(data: List<EventFeedModel>) {
        var timeDelay: Long
        while (true){
            data.forEach {
                val main = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault())
                val scheduleEndDate = main.parse(it.end)
                timeDelay = scheduleEndDate!!.time - Date().time
                delay(timeDelay)
//                Commands.reCreate.emit(true)
            }
            delay(5000)
        }
    }
    inner class Reorder : Comparator<EventFeedModel> {
        override fun compare(o1: EventFeedModel?, o2: EventFeedModel?): Int {
            var d1: Date? = null
            var d2: Date? = null
            try {
                val df = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault())
                d1 = df.parse(o1?.end!!)
                d2 = df.parse(o2?.end!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (d1 != null)
                if (d1.before(d2))
                    return -1
            return 1
        }
    }

    inner class FeedTimeComparator : Comparator<EventFeedModel> {
        override fun compare(o1: EventFeedModel?, o2: EventFeedModel?): Int {
            o1?.orderNo?.let {
                if (it < o2?.orderNo!!)
                    return -1
            }
            return 1
        }
    }



}