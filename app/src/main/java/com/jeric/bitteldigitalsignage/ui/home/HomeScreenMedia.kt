package com.jeric.bitteldigitalsignage.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.databinding.FragmentHomeMediaBinding
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class HomeScreenMedia : Fragment() {
    private var _binding: FragmentHomeMediaBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val mediaFragment by lazy { HomeScreenMediaFragment() }
    var zone = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Call once, outside collectors
        homeViewModel.getZoneMediaModel()
        homeViewModel.getZoneModel()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.zoneMediaFlow.collectLatest { zoneMedia->
                    launch {
                        homeViewModel.zoneState.collectLatest { zoneState ->
                            displayMedia(zoneState, zoneMedia)
                        }
                    }
                }
            }
        }
    }

    private suspend fun displayMedia(zones: List<ZoneModel>?, zoneMediaModel: List<ZoneMediaModel>?) {
        val dateToday = SimpleDateFormat("EEE", Locale.getDefault()).format(Date())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayDate = dateFormat.parse(dateFormat.format(Date())) ?: return

        val filteredZones = zones
            ?.filter { it.zone == zone.toInt() }
            ?.filter { zoneItem ->
                val calendar = Calendar.getInstance().apply { add(Calendar.DATE, -1) }
                val yesterday = dateFormat.format(calendar.time)
                val endDate = dateFormat.parse(zoneItem.end ?: yesterday) ?: return@filter false
                todayDate <= endDate
            }
            ?.filter { zoneItem ->
                when (dateToday) {
                    "Mon" -> zoneItem.mon == 1
                    "Tue" -> zoneItem.tue == 1
                    "Wed" -> zoneItem.wed == 1
                    "Thu" -> zoneItem.thu == 1
                    "Fri" -> zoneItem.fri == 1
                    "Sat" -> zoneItem.sat == 1
                    "Sun" -> zoneItem.sun == 1
                    else -> false
                }
            }

        val filteredMedia = zoneMediaModel
            ?.filter { it.zone == zone.toInt() }
            ?.filter { !(it.timeStart.isNullOrEmpty() && it.timeEnd.isNullOrEmpty()) }
            ?.filter { med ->
                try {
                    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    val format = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault()) // Use HH, not kk
                    val ends = "$today ${med.timeEnd}"
                    val endDate = format.parse(ends)
                    endDate != null && Date().before(endDate)
                } catch (e: Exception) {
                    false // Exclude item if date parsing fails
                }
            }
            ?.sortedWith(MediaZoneTimeComparator())


        if (filteredZones.isNullOrEmpty() || filteredMedia.isNullOrEmpty()) return

        loadMedia(filteredZones, filteredMedia)
    }

    private suspend fun loadMedia(zones: List<ZoneModel>, zoneMediaModel: List<ZoneMediaModel>) {
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val mainFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // Correct format is HH, not kk

        while (true) {
            zones.forEach  {sched ->
                try {
                    val startDate = sched.start?.let { dateFormat.parse(it) }
                    val endDate = sched.end?.let { dateFormat.parse(it) }

                    if (startDate != null && endDate != null) {
                        zoneMediaModel.forEach { media ->
                            val start = "$todayDate ${media.timeStart}"
                            val end = "$todayDate ${media.timeEnd}"

                            val scheduleStartDate = mainFormat.parse(start)
                            val scheduleEndDate = mainFormat.parse(end)
                            val now = Date().time

                            if (scheduleStartDate != null && scheduleEndDate != null) {
                                val startDelay = scheduleStartDate.time - now
                                if (startDelay > 0) {
                                    delay(startDelay)
                                }

                                val adjustedNow = Date().time
                                val displayDuration = scheduleEndDate.time - adjustedNow

                                if (displayDuration > 0) {
                                    mediaFragment.signageMediaData = media
                                    childFragmentManager.beginTransaction()
                                        .replace(R.id.container, mediaFragment, "display${media.zone}")
                                        .commitAllowingStateLoss()

                                    delay(displayDuration)

                                    childFragmentManager.beginTransaction()
                                        .remove(mediaFragment)
                                        .commitAllowingStateLoss()
                                }
                            }
                            delay(500)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                delay(500)
            }
            delay(1000)
        }
    }

    inner class MediaZoneTimeComparator : Comparator<ZoneMediaModel> {
        override fun compare(o1: ZoneMediaModel?, o2: ZoneMediaModel?): Int {
            return try {
                val df = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val d1 = df.parse(o1?.timeEnd ?: "") ?: return 1
                val d2 = df.parse(o2?.timeEnd ?: "") ?: return -1
                d1.compareTo(d2)
            } catch (e: Exception) {
                1
            }
        }
    }
}