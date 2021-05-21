package com.sners.snowforecast.weather

import android.graphics.Color

//  I actually want the Swift Tuple here and make an array
//  Probably want to keep learning
const val CHART_WHITE = -0x1000000
const val CHART_LIGHT_GREY = -0x3f3f40
const val CHART_PALE_BLUE = -0x663301 //	pale blue
const val CHART_MEDIUM_BLUE = -0xff7f01 //	medium blue
const val CHART_DARK_BLUE = -0xffff9a //	dark blue
const val CHART_DARK_PURPLE= -0x99ff9a //	dark purple

/**
 * A base class representing the different levels of precipitation in the graphs
 * - Enum does not give the flexibility required.
 * - This is something that is not intended to be extended for any other purpose
 * - so a sealed class is ideal
 */
sealed class PrecipLevels (val cutoff: Float, val name : String,
                           val chartColour : Int  = Color.BLUE,
                            val yAxisMultiplier : Float = 10f){
}

/**
 * No precipitation
 * chartColour = white
 */
object PrecipNone : PrecipLevels(0.001f, "None",
    CHART_WHITE, 20f)  {
}

/**
 * Very Light precipitation
 * chartColour = light grey
 */
object PrecipVeryLight : PrecipLevels(0.9f, "Very Light",
    CHART_LIGHT_GREY, 5f)  {
}

/**
 * Light precipitation
 */
object PrecipLight : PrecipLevels(2.5f, "Light",
    CHART_PALE_BLUE, 4f)  {
}

/**
 * Moderate precipitation
 */
object PrecipModerate : PrecipLevels(6.25f, "Moderate",
    CHART_MEDIUM_BLUE, 3f)  {
}

/**
 * Heavy precipitation
 */
object PrecipHeavy : PrecipLevels(25.0f, "Heavy",
    CHART_DARK_BLUE, 2f)  {
}

/**
 * Flood
 */
object PrecipFlood : PrecipLevels(Float.MAX_VALUE, "!!! EVACUATE !!!",
    CHART_DARK_PURPLE, 1f)  {
}
