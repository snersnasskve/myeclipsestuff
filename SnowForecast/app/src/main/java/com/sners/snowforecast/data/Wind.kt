package com.sners.snowforecast.data

import java.util.*

/**
 * Everything about wind
 * @param hourlyData array of hourly data
 * @param currently current weather data
 */
class Wind(private val hourlyData: ArrayList<IntervalData>, private val currently: Currently) {

    /**
     *  @property beaufortScaleUppers Upper limits of beaufort wind speeds
     */
    private var beaufortScaleUppers = arrayOf(1.0, 3.0, 7.0, 12.0, 17.0, 24.0, 30.0, 38.0, 46.0, 54.0, 63.0, 73.0)


    /**
     *  @property windSpeed Wind speed - reads from data object as appropriate
     */
    private val windSpeed: Float
        get() {
            var wind = currently.windSpeed
            if (wind < 1) {
                wind = hourlyData[0].windSpeed
            }
            return wind.toFloat()
        }

    /**
     *  @property windGusts Wind speed - reads from data object as appropriate
     */
    private val windGusts: Float
        get() {
            return if (currently.windGusts >= 0) currently.windGusts.toFloat()
            else hourlyData[0].windGusts
        }

    /**
     *  @property windGusts Wind speed - reads from data object as appropriate
     */
    private val windDir: Float
        get() {

            return if (currently.windDir >= 0) currently.windDir.toFloat()
            else hourlyData[0].windDir
        }

    /**
     * Get the wind details
     * @return Wind details string
     */
    fun getDetails(): String {
        //  eg 1.2 mph (gusts: 4 mph) NE
        //return "$windSpeed mph (gusts: $windGusts) ${windDirToString(windDir)}"
        return String.format(
            "%.1f mph (gusts %.1f) %s",
            windSpeed,
            windGusts,
            windDirToString(windDir))
    }

    /**
     * Get the wind speed as mph
     * @return Wind converted to mph
     */
    private fun getSpeedMph(): Double {
        return windSpeed * WeatherConstants.KM_TO_MPH_CONVERSION
    }

    /**
     * Get the wind speed as Beaufort
     * @return Wind converted to Beaufort number
     */
    fun getSpeedBeaufort(): Float {
        return windSpeedToBeaufort(getSpeedMph()).toFloat()
    }

    /**
     * Get the wind speed as Beaufort string
     * @return Wind converted to Beaufort string
     */
    fun getSpeedBeaufortString(): String {
        return "${getSpeedBeaufort()}"
    }

    /**
     * Wind Speed to Beaufort number conversion
     * @param windSpeed Wind Speed in mph
     * @return Wind converted to Beaufort number
     */
    private fun windSpeedToBeaufort(windSpeed: Double): Int {
        //	Wind speed is meters per second

        val beaufortValue = beaufortScaleUppers.indexOfFirst ({ windSpeed < it })
        return beaufortValue
    }

    fun windDirToString(windDir : Float): String {
            val mainDir = Math.round(windDir / 90)
            var dir = when (mainDir % 4) {
                0 -> WeatherConstants.NORTH
                1 -> WeatherConstants.EAST
                2 -> WeatherConstants.SOUTH
                3 -> WeatherConstants.WEST
                else -> ""
            }
            val secondDir = Math.round(windDir / 45)
            dir = dir + when (secondDir % 8) {
                1 -> WeatherConstants.NORTH
                3 -> WeatherConstants.EAST
                5 -> WeatherConstants.SOUTH
                7 -> WeatherConstants.WEST
                else -> ""
            }
            return dir
        }
}