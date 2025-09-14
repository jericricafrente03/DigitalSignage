package com.jeric.bitteldigitalsignage.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.databinding.FragmentHomeBinding
import com.jeric.bitteldigitalsignage.datastore.model.STB
import com.jeric.bitteldigitalsignage.network.domain.model.SignageDataModel
import com.jeric.bitteldigitalsignage.ui.components.LayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreen : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    companion object {
        var zoneSize = 0
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.getSignageDataModel()
        homeViewModel.getDailyWeather()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.signageDataState.collectLatest {
                    layoutDisplay(it)
                }
            }
        }
    }

    private fun layoutDisplay(layoutData: SignageDataModel?) {
        layoutData ?: return

        with(binding) {
            val imageUri = "${STB.HOST}:${STB.PORT}/${layoutData.imgUri}"
            ivBg.load(imageUri)
            val layoutId = LayoutManager.getLayoutManager().getLayout(layoutData.layoutId)

            if (layoutId == 0) {
                Snackbar.make(root, "Layout not found for layoutId: ${layoutData.layoutId}", Snackbar.LENGTH_SHORT).show()
                return
            }

            try {
                parent.removeAllViews()
                val signageLayout = layoutInflater.inflate(layoutId, parent, false)
                parent.addView(signageLayout)
                zoneSize = layoutData.layout?.zones ?: 1

                val zoneCount = layoutData.layout?.zones ?: 1
                for (zone in 1..zoneCount) {
                    val zoneFragment = HomeScreenMedia()
                    zoneFragment.zone = zone.toString()
                    when (zone) {
                        1 -> attachFragment(zoneFragment, R.id.zone_01, "zone1")
                        2 -> attachFragment(zoneFragment, R.id.zone_02, "zone2")
                        3 -> attachFragment(zoneFragment, R.id.zone_03, "zone3")
                    }
                }
            } catch (e: Resources.NotFoundException) {
                Snackbar.make(root, "Error loading layout $e", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun attachFragment(fragment: Fragment, containerId: Int, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment, tag)
            .commit()
    }
}