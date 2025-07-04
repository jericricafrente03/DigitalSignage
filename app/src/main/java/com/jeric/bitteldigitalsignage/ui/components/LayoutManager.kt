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
        36 -> R.layout.layout_036
        37 -> R.layout.layout_037
        38 -> R.layout.layout_038
        43 -> R.layout.layout_043
        46 -> R.layout.layout_046
        47 -> R.layout.layout_047
        48 -> R.layout.layout_048
        49 -> R.layout.layout_049
        60 -> R.layout.layout_014
        else -> 0
    }

    fun getFeedLayout(id: Int): Int = when (id) {
        14 -> R.layout.feed_014
        21 -> R.layout.feed_021
        22 -> R.layout.feed_022
        23 -> R.layout.feed_023
        24 -> R.layout.feed_024
        25 -> R.layout.feed_025
        41 -> R.layout.feed_041
        42 -> R.layout.feed_042
        44 -> R.layout.feed_044
        50 -> R.layout.feed_054
        57 -> R.layout.feed_057
        58 -> R.layout.feed_058
        59 -> R.layout.feed_059
        else -> 0
    }

    fun getFeedItemLayout(id: Int): Int = when (id) {
        14 -> R.layout.feed_item_014
        21 -> R.layout.feed_item_021
        22 -> R.layout.feed_item_022
        23 -> R.layout.feed_item_023
        24 -> R.layout.feed_item_024
        25 -> R.layout.feed_item_025
        26 -> R.layout.feed_item_022a
        41 -> R.layout.feed_item_041
        42 -> R.layout.feed_item_042
        44 -> R.layout.feed_item_044
        50 -> R.layout.feed_item_054
        57 -> R.layout.feed_item_057
        58 -> R.layout.feed_item_058
        59 -> R.layout.feed_item_059
        else -> 0
    }

    fun getTimeLayout(id: Int): Int = when (id) {
        15 -> R.layout.time_fragment_015
        16 -> R.layout.time_fragment_016
        17 -> R.layout.time_fragment_017
        18 -> R.layout.time_fragment_018
        19 -> R.layout.time_fragment_019
        20 -> R.layout.time_fragment_020
        39 -> R.layout.time_fragment_039
        55 -> R.layout.time_fragment_055
        else -> 0
    }

    fun getWeatherLayout(id: Int): Int = when (id) {
        32 -> R.layout.weather_32
        33 -> R.layout.weather_33
        35 -> R.layout.weather_35
        34 -> R.layout.weather_40
        52 -> R.layout.weather_52
        56 -> R.layout.weather_56
        else -> 0
    }
}