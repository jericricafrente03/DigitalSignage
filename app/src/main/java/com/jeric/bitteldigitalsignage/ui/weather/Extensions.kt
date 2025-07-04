package com.jeric.bitteldigitalsignage.ui.weather

import com.jeric.bitteldigitalsignage.R
import java.util.Locale


/***
 *
 *
 *
 *
 *
 *
 *
$weatherConditions = [
            0 => 'Clear sky',
            1 => 'Mainly clear',
            2 => 'Partly cloudy',
            3 => 'Overcast',
            45 => 'Fog',
            48 => 'Depositing rime fog',
            51 => 'Drizzle: Light intensity',
            53 => 'Drizzle: Moderate intensity',
            55 => 'Drizzle: Dense intensity',
            56 => 'Freezing Drizzle: Light intensity',
            57 => 'Freezing Drizzle: Dense intensity',
            61 => 'Rain: Slight intensity',
            63 => 'Rain: Moderate intensity',
            65 => 'Rain: Heavy intensity',
            66 => 'Freezing Rain: Light intensity',
            67 => 'Freezing Rain: Heavy intensity',
            71 => 'Snow fall: Slight intensity',
            73 => 'Snow fall: Moderate intensity',
            75 => 'Snow fall: Heavy intensity',
            77 => 'Snow grains',
            80 => 'Rain showers: Slight',
            81 => 'Rain showers: Moderate',
            82 => 'Rain showers: Violent',
            85 => 'Snow showers: Slight',
            86 => 'Snow showers: Heavy',
            95 => 'Thunderstorm: Slight',
            96 => 'Thunderstorm: Moderate',
            99 => 'Thunderstorm: Violent'
        ];
 **
 *
 *
 *
 *
*
*/

object Extensions {
    fun getDrawableResource(drawableResId: String?): Int {
        return when(drawableResId){
            "0" -> R.drawable.w01d
            "1" -> R.drawable.w04d
            "2" -> R.drawable.w02d
            "3" -> R.drawable.w50d
            "45" -> R.drawable.w50n
            "48" -> R.drawable.w09d
            "51" -> R.drawable.w09d
            "53" -> R.drawable.w09d
            "55" -> R.drawable.w09d
            "56" -> R.drawable.w13d
            "57" -> R.drawable.w13d
            "61" -> R.drawable.w10n
            "63" -> R.drawable.w10n
            "65" -> R.drawable.w10n
            "66" -> R.drawable.w13n
            "67" -> R.drawable.w13n
            "71" -> R.drawable.w09n
            "73" -> R.drawable.w09n
            "75" -> R.drawable.w09n
            "77" -> R.drawable.w09n
            "80" -> R.drawable.w10n
            "81" -> R.drawable.w10n
            "82" -> R.drawable.w10n
            "85" -> R.drawable.w09d
            "86" -> R.drawable.w09d
            "95" -> R.drawable.w11n
            "96" -> R.drawable.w11n
            "99" -> R.drawable.w11n
            else -> R.drawable.w01d
        }
    }

    fun capitalizeWord(data: String?): String {
        val words = data?.split(" ")
        var newStr = ""
        words?.forEach { word ->
            newStr += word.replaceFirstChar {
                if (it.isLowerCase())
                    it.titlecase(Locale.getDefault())
                else it.toString()
            } + " "
        }
        return newStr.trimEnd()
    }
}