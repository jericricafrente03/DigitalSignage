package com.jeric.bitteldigitalsignage.ui.home

import android.annotation.SuppressLint
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
import coil.load
import coil.request.CachePolicy
import com.jeric.bitteldigitalsignage.databinding.DigitalMediaLayoutBinding
import com.jeric.bitteldigitalsignage.databinding.FragmentHomeMediaBinding
import com.jeric.bitteldigitalsignage.datastore.model.STB
import com.jeric.bitteldigitalsignage.network.domain.model.MediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.SignageDataModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneModel
import com.jeric.bitteldigitalsignage.network.util.DataState
import com.jeric.bitteldigitalsignage.ui.components.MediaType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Comparator
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class HomeScreenMediaFragment : Fragment() {
    private var _binding: DigitalMediaLayoutBinding? = null
    private val binding get() = _binding!!

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
                    MediaType.VIDEO -> {}
                    MediaType.WEATHER -> {}
                    MediaType.FEED -> {}
                    MediaType.WEATHER_FORECAST -> {}
                    MediaType.TV -> {}
                    MediaType.SCROLLING -> {}
                }
            }
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





}