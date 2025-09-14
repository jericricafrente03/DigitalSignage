package com.jeric.bitteldigitalsignage.ui.components

import com.jeric.bitteldigitalsignage.R

class LayoutManager {

    companion object {
        @Volatile
        private var instance: LayoutManager? = null

        fun getLayoutManager(): LayoutManager {
            return instance ?: synchronized(this) {
                instance ?: LayoutManager().also { instance = it }
            }
        }
    }

    fun getLayout(id: Int): Int = when (id) {
        1 -> R.layout.layout_001
        2 -> R.layout.layout_002
        3 -> R.layout.layout_003
        4 -> R.layout.layout_004
        5 -> R.layout.layout_005
        6 -> R.layout.layout_006
        7 -> R.layout.layout_007
        8 -> R.layout.layout_008
        9 -> R.layout.layout_009
        10 -> R.layout.layout_010
        11 -> R.layout.layout_011
        12 -> R.layout.layout_012
        13 -> R.layout.layout_013
        14 -> R.layout.layout_036
        15 -> R.layout.layout_037
        16 -> R.layout.layout_038
        17 -> R.layout.layout_043
        18 -> R.layout.layout_046
        19 -> R.layout.layout_047
        20 -> R.layout.layout_048
        21 -> R.layout.layout_049
        22 -> R.layout.layout_014
        else -> 0
    }

    fun getFeedLayout(id: Int): Int = when (id) {
        37 -> R.layout.feed_014
        38 -> R.layout.feed_021
        39 -> R.layout.feed_022
        40 -> R.layout.feed_023
        41 -> R.layout.feed_024
        42 -> R.layout.feed_025
        43 -> R.layout.feed_041
        44 -> R.layout.feed_042
        45 -> R.layout.feed_044
        46 -> R.layout.feed_054
        47 -> R.layout.feed_057
        48 -> R.layout.feed_058
        49 -> R.layout.feed_059
        else -> 0
    }

    fun getFeedItemLayout(id: Int): Int = when (id) {
        37 -> R.layout.feed_item_014
        38 -> R.layout.feed_item_021
        39 -> R.layout.feed_item_022
        40 -> R.layout.feed_item_023
        41 -> R.layout.feed_item_024
        42 -> R.layout.feed_item_025
        26 -> R.layout.feed_item_022a
        43 -> R.layout.feed_item_041
        44 -> R.layout.feed_item_042
        45 -> R.layout.feed_item_044
        46 -> R.layout.feed_item_054
        47 -> R.layout.feed_item_057
        48 -> R.layout.feed_item_058
        49 -> R.layout.feed_item_059
        else -> 0
    }

    fun getTimeLayout(id: Int): Int = when (id) {
        23 -> R.layout.time_fragment_015
        24 -> R.layout.time_fragment_016
        25 -> R.layout.time_fragment_017
        26 -> R.layout.time_fragment_018
        27 -> R.layout.time_fragment_019
        28 -> R.layout.time_fragment_020
        29 -> R.layout.time_fragment_039
        30 -> R.layout.time_fragment_055
        else -> 0
    }

    fun getWeatherLayout(id: Int): Int = when (id) {
        31 -> R.layout.weather_32
        32 -> R.layout.weather_33
        33 -> R.layout.weather_40
        34 -> R.layout.weather_35
        35 -> R.layout.weather_52
        36 -> R.layout.weather_56
        else -> 0
    }
}