package com.sners.snowforecast.weather

import com.sners.snowforecast.data.Currently
import com.sners.snowforecast.data.IntervalData
import java.util.*
import kotlin.math.roundToInt

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
            var wind = hourlyData[0].windSpeed
            if (wind < 1) {
                 wind = currently.windSpeed
            }
            return wind.toFloat()
        }

    /**
     *  @property windGusts Wind speed - reads from data object as appropriate
     */
    private val windGusts: Float
        get() {
            return if (hourlyData[0].windGusts >= 0) hourlyData[0].windGusts
            else currently.windGusts
        }

    /**
     *  @property windGusts Wind speed - reads from data object as appropriate
     */
    private val windDir: Float
        get() {

            return if (currently.windDir >= 0) currently.windDir
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
            "%.1f mph (gusts %.1f) %.0f - %s",
            getSpeedMph(),
            getGustsMph(),
            windDir,
            windDirToString(windDir)
        )
    }

    /**
     * Get the wind speed as mph
     * @return Wind converted to mph
     */
    private fun getSpeedMph(): Double {
        return windSpeed * WeatherConstants.KM_TO_MPH_CONVERSION
    }

    /**
     * Get the wind gusts as mph
     * @return Gusts converted to mph
     */
    private fun getGustsMph(): Double {
        return windGusts * WeatherConstants.KM_TO_MPH_CONVERSION
    }

    /**
     * Get the wind speed as Beaufort
     * @return Wind converted to Beaufort number
     */
    fun getSpeedBeaufort(): Int {
        return windSpeedToBeaufort(getSpeedMph())
    }

    /**
     * Wind Speed to Beaufort number conversion
     * @param windSpeed Wind Speed in mph
     * @return Wind converted to Beaufort number
     */
    private fun windSpeedToBeaufort(windSpeed: Double): Int {

        val beaufortValue = beaufortScaleUppers.indexOfFirst({ windSpeed < it })
        return beaufortValue
    }

    /**
     * Wind direction to string
     * https://snersbots.co.uk/index.php/weather-app-wind-direction/
     * @param windDir Wind direction degrees
     * @return A string representing wind direction
     */
    private fun windDirToString(windDir: Float): String {
        if (windDir == 0f) {
            //  If we have invalid data it comes through as 0 - then we don't want a direction
            return "{$WeatherConstants.NORTH}(?)"
        }
        val directionPointer = Math.round(windDir / 45).toFloat()
        val valA = (directionPointer / 2f).roundToInt()
        val valB = Math.abs(valA - 1)

        if (Math.round(directionPointer) % 2 == 0) {
            //  wind is due something
            return getWindAngleFor(valA)
        } else if (valB % 2 == 1) {
            return getWindAngleFor(valA) + getWindAngleFor(valB)
        } else {
            return getWindAngleFor(valB) + getWindAngleFor(valA)
        }
    }

    /**
     * Get wind angle for
     * @param pointer A number from 0 to 3
     * @return Direction as string
     */
    fun getWindAngleFor(pointer: Int): String {

        return when (pointer % 4) {
            0 -> WeatherConstants.NORTH
            1 -> WeatherConstants.EAST
            2 -> WeatherConstants.SOUTH
            3 -> WeatherConstants.WEST
            else -> ""
        }
    }
}